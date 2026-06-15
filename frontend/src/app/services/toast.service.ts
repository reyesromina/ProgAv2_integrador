import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ToastService {
  success(message: string): void {
    // Minimal implementation: replace with your UI toast integration
    console.log('[Toast] success:', message);
    try { window.alert(message); } catch {}
  }

  error(message: string): void {
    console.error('[Toast] error:', message);
    try { window.alert(message); } catch {}
  }

  showSuccess(message: string): void {
    this.success(message);
  }

  showError(message: string): void {
    this.error(message);
  }
}

