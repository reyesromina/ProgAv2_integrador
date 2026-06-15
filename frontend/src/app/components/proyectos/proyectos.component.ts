import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-proyectos',
  standalone: true,
  imports: [CommonModule],
  template: `
    <h2>Proyectos</h2>
    <p>Bienvenido a la vista de proyectos.</p>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProyectosComponent {}
