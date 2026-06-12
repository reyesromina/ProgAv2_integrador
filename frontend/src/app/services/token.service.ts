import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class TokenService {
  private readonly ACCESS_KEY = 'app_access_token';
  private readonly REFRESH_KEY = 'app_refresh_token';

  setTokens(accessToken: string, refreshToken: string): void {
    try {
      localStorage.setItem(this.ACCESS_KEY, accessToken);
      localStorage.setItem(this.REFRESH_KEY, refreshToken);
    } catch (e) {
      // Silencioso: si el almacenamiento falla, no bloqueamos la UX
      console.error('TokenService: error saving tokens', e);
    }
  }

  private get storage(): Storage | null {
  return typeof localStorage !== 'undefined' ? localStorage : null;
}

getAccessToken(): string | null {
  return this.storage?.getItem(this.ACCESS_KEY) ?? null;
}

getRefreshToken(): string | null {
  return this.storage?.getItem(this.REFRESH_KEY) ?? null;
}

clearTokens(): void {
  this.storage?.removeItem(this.ACCESS_KEY);
  this.storage?.removeItem(this.REFRESH_KEY);
}
}
