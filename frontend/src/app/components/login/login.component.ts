import { Component, ChangeDetectionStrategy, inject, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators, FormControl } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { TokenService } from '../../services/token.service';
import { ToastService } from '../../services/toast.service';
import { Router } from '@angular/router';

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

  emailInvalid       = computed(() => !!this.form.get('email')?.touched && !!this.form.get('email')?.invalid);
emailRequiredError = computed(() => !!this.form.get('email')?.touched && !!this.form.get('email')?.errors?.['required']);
emailFormatError   = computed(() => !!this.form.get('email')?.touched && !!this.form.get('email')?.errors?.['email']);

passwordInvalid       = computed(() => !!this.form.get('password')?.touched && !!this.form.get('password')?.invalid);
passwordRequiredError = computed(() => !!this.form.get('password')?.touched && !!this.form.get('password')?.errors?.['required']);

isFormInvalid = computed(() => this.form.invalid);
  

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
