import { Component, ChangeDetectionStrategy, inject, computed, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormControl } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';
import { ToastService } from '../../services/toast.service';
import { Router } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { merge } from 'rxjs';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
 
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class LoginComponent {

private readonly fb = inject(FormBuilder);
private readonly auth = inject(AuthService);
private readonly tokenService = inject(TokenService);
private readonly toast = inject(ToastService);
private readonly router = inject(Router);

  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });
  private readonly touchTrigger = signal(0);

private readonly formChanges = toSignal(
  merge(this.form.statusChanges, this.form.valueChanges),
  { initialValue: null }
);
 emailInvalid       = computed(() => { this.formChanges(); this.touchTrigger(); return !!this.form.get('email')?.touched && !!this.form.get('email')?.invalid; });
emailRequiredError = computed(() => { this.formChanges(); this.touchTrigger(); return !!this.form.get('email')?.touched && !!this.form.get('email')?.errors?.['required']; });
emailFormatError   = computed(() => { this.formChanges(); this.touchTrigger(); return !!this.form.get('email')?.touched && !!this.form.get('email')?.errors?.['email']; });

passwordInvalid       = computed(() => { this.formChanges(); this.touchTrigger(); return !!this.form.get('password')?.touched && !!this.form.get('password')?.invalid; });
passwordRequiredError = computed(() => { this.formChanges(); this.touchTrigger(); return !!this.form.get('password')?.touched && !!this.form.get('password')?.errors?.['required']; });

isFormInvalid = computed(() => { this.formChanges(); return this.form.invalid; });

// método que dispara el recálculo al perder el foco
onBlur(): void {
  this.touchTrigger.update(v => v + 1);
}
  login(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const email = this.form.value.email || '';
    const password = this.form.value.password || '';

    this.auth.login(email, password).subscribe({
      next: (res) => {
        try {
          this.tokenService.setTokens(res.accessToken, res.refreshToken);
          localStorage.setItem('userId', String(res.userId));
          //modificación temporal para guardar el userId, idealmente el backend debería
          // incluirlo en el token o proporcionar un endpoint para obtenerlo después del login
        } catch (e) {
          console.error('Error saving tokens', e);
        }
        this.toast.success('Inicio de sesión exitoso');
        this.router.navigate(['/proyectos']);
      },
      error: (err) => {
        if (err?.status === 401) {
          this.toast.error('Credenciales incorrectas');
        } else {
          this.toast.error('Error al autenticar usuario');
        }
      }
    });
  }
}
