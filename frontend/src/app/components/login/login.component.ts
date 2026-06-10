import { Component, ChangeDetectionStrategy, inject } from '@angular/core';
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
  form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  // expose controls as properties to avoid calling methods in template
  emailControl = this.form.get('email') as FormControl;
  passwordControl = this.form.get('password') as FormControl;

  constructor(

    private auth: AuthService,
    private tokenService: TokenService,
    private toast: ToastService,
    private router: Router
  ) {}

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
