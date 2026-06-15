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

  registerUser(email: string, password: string): Observable<RegisterResponse> {
    const request: RegisterRequest = { email, password };
    return this.http.post<RegisterResponse>(`${environment.apiUrl}/users/register`, request);
  }

    verificarCodigo(email: string, code: string): Observable<VerifyCodeResponse> {
    const request: VerifyCodeRequest = { email, code };
    return this.http.post<VerifyCodeResponse>(`${environment.apiUrl}/users/verify-code`, request);
  }
}
