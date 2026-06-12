import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class TokenService {
  private readonly ACCESS_KEY = 'app_access_token';
  private readonly REFRESH_KEY = 'app_refresh_token';
  
setTokens(accessToken: string, refreshToken: string): void {
  try {
    localStorage.setItem(this.ACCESS_KEY, accessToken);
    // refreshToken idealmente va en httpOnly cookie manejada por el backend
    // Si el backend no lo soporta aún, documentar la deuda técnica:
    localStorage.setItem(this.REFRESH_KEY, refreshToken); // TODO: migrar a httpOnly cookie
  } catch (e) {
    console.error('TokenService: error saving tokens', e);
  }
}

  getAccessToken(): string | null {
    return localStorage.getItem(this.ACCESS_KEY);
  }

  getRefreshToken(): string | null {
    return localStorage.getItem(this.REFRESH_KEY);
  }

  clearTokens(): void {
    localStorage.removeItem(this.ACCESS_KEY);
    localStorage.removeItem(this.REFRESH_KEY);
  }
}
