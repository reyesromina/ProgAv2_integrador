import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

 registrarUsuario(email: string, password: string): Observable<RegisterResponse> {
  const request: RegisterRequest = { email, password };
  return this.http.post<RegisterResponse>(`${this.apiUrl}/users/register`, request);
}

  verificarCodigo(email: string, code: string): Observable<VerifyCodeResponse> {
    const request: VerifyCodeRequest = { email, code };
    return this.http.post<VerifyCodeResponse>(`${this.apiUrl}/users/verify-code`, request);
  }
}
