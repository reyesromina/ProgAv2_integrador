import { Component, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-proyecto-detalle',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="container mt-5">
      <div class="alert alert-info">
        <h4>Componente de Detalle del Proyecto</h4>
        <p>Este componente se implementará por separado.</p>
      </div>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProyectoDetalleComponent {}
