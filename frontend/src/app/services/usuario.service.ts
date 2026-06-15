import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface RegisterRequest {
  email: string;
  password: string;
}

export interface RegisterResponse {
  accessToken: string;
  refreshToken: string;
}

export interface VerifyCodeRequest {
  email: string;
  code: string;
}

export interface VerifyCodeResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  

  constructor(private http: HttpClient) {}

  // Nueva firma conforme al SPEC: retorna tokens en caso de éxito
  registerUser(email: string, password: string): Observable<RegisterResponse> {
    const request: RegisterRequest = { email, password };
    return this.http.post<RegisterResponse>(`${environment.apiUrl}/users/register`, request);
  }

  // Mantener compatibilidad con el nombre en español si otras partes lo usan
  registrarUsuario(email: string, password: string): Observable<RegisterResponse> {
    return this.registerUser(email, password);
  }

  verificarCodigo(email: string, code: string): Observable<VerifyCodeResponse> {
    const request: VerifyCodeRequest = { email, code };
    return this.http.post<VerifyCodeResponse>(`${environment.apiUrl}/users/verify-code`, request);
  }
}
