import { Component, OnInit, ChangeDetectionStrategy, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UsuarioService } from '../../services/usuario.service';

type RegistroState = 'formulario' | 'validacion-codigo' | 'exito' | 'error';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegistroComponent implements OnInit {
  formulario!: FormGroup;
  estado = signal<RegistroState>('formulario');
  cargando = signal(false);
  mensajeError = signal('');
  emailRegistrado = signal('');

  tieneErrorEmail = computed(() => {
    const control = this.formulario.get('email');
    return control ? control.invalid && control.touched : false;
  });

  tieneErrorPassword = computed(() => {
    const control = this.formulario.get('password');
    return control ? control.invalid && control.touched : false;
  });

  tieneErrorCodigo = computed(() => {
    const control = this.formulario.get('codigo');
    return control ? control.invalid && control.touched : false;
  });

  constructor(
    private fb: FormBuilder,
    private usuarioService: UsuarioService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.inicializarFormulario();
  }

  private inicializarFormulario(): void {
    this.formulario = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  registrar(): void {
    if (this.formulario.invalid) {
      this.marcarCamposComoTocados();
      return;
    }

    this.cargando.set(true);
    this.mensajeError.set('');

    const { email, password } = this.formulario.value;

    this.usuarioService.registrarUsuario(email, password).subscribe({
      next: () => {
        this.cargando.set(false);
        this.emailRegistrado.set(email);
        this.estado.set('validacion-codigo');
        this.formulario.reset();
        this.inicializarFormularioValidacion();
      },
      error: (error) => {
        this.cargando.set(false);
        this.estado.set('error');
        this.mensajeError.set(this.extraerMensajeError(error));
      }
    });
  }

  private inicializarFormularioValidacion(): void {
    this.formulario = this.fb.group({
      codigo: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  verificarCodigo(): void {
    if (this.formulario.invalid) {
      this.marcarCamposComoTocados();
      return;
    }

    this.cargando.set(true);
    this.mensajeError.set('');

    const { codigo } = this.formulario.value;

    this.usuarioService.verificarCodigo(this.emailRegistrado(), codigo).subscribe({
      next: () => {
        this.cargando.set(false);
        this.estado.set('exito');
        setTimeout(() => {
          this.router.navigate(['/login/user']);
        }, 2000);
      },
      error: (error) => {
        this.cargando.set(false);
        this.estado.set('error');
        this.mensajeError.set(this.extraerMensajeError(error));
      }
    });
  }

  volverAlFormulario(): void {
    this.estado.set('formulario');
    this.inicializarFormulario();
    this.formulario.reset();
    this.mensajeError.set('');
  }

  private marcarCamposComoTocados(): void {
    Object.keys(this.formulario.controls).forEach(key => {
      this.formulario.get(key)?.markAsTouched();
    });
  }

  private extraerMensajeError(error: any): string {
    if (error.status === 400) {
      if (error.error?.message) {
        return error.error.message;
      }
      return 'Usuario existente, email inválido o campo incompleto';
    }
    return 'Error en la solicitud. Por favor, intenta nuevamente.';
  }
}
