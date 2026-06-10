import { Component, OnInit, ChangeDetectionStrategy, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProjectService } from '../../services/project.service';
import { ToastService } from '../../services/toast.service';

type CreateProjectState = 'formulario' | 'exito' | 'error';

@Component({
  selector: 'app-create-project',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CreateProjectComponent implements OnInit {
  formulario!: FormGroup;
  estado = signal<CreateProjectState>('formulario');
  cargando = signal(false);
  mensajeError = signal('');

  tieneErrorNombre = computed(() => {
    const control = this.formulario.get('name');
    return control ? control.invalid && control.touched : false;
  });

  tieneErrorDescripcion = computed(() => {
    const control = this.formulario.get('description');
    return control ? control.invalid && control.touched : false;
  });

  constructor(
    private fb: FormBuilder,
    private projectService: ProjectService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.inicializarFormulario();
  }

  private inicializarFormulario(): void {
    this.formulario = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      description: ['', [Validators.required, Validators.maxLength(500)]]
    });
  }

  crearProyecto(): void {
    if (this.formulario.invalid) {
      this.marcarCamposComoTocados();
      return;
    }

    this.cargando.set(true);
    this.mensajeError.set('');

    const { name, description } = this.formulario.value;

    this.projectService.createProject(name, description).subscribe({
      next: () => {
        this.cargando.set(false);
        this.toastService.success('Proyecto creado exitosamente');
        this.formulario.reset();
        this.estado.set('exito');
        setTimeout(() => {
          this.router.navigate(['/proyectos']);
        }, 2000);
      },
      error: (error) => {
        this.cargando.set(false);
        this.mensajeError.set(this.extraerMensajeError(error));
        this.toastService.error(this.extraerMensajeError(error));
      }
    });
  }

  volverAlFormulario(): void {
    this.estado.set('formulario');
    this.formulario.reset();
    this.mensajeError.set('');
    this.router.navigate(['/proyectos']);
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
      return 'Datos inválidos. Verifica que el nombre tenga entre 3 y 100 caracteres.';
    }
    if (error.status === 401) {
      return 'No estás autenticado. Por favor, inicia sesión.';
    }
    return 'Error al crear el proyecto. Por favor, intenta nuevamente.';
  }
}
