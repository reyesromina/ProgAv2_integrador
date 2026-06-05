import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RegistroComponent } from './components/registro/registro.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RegistroComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('progAv_integrador_front');
}
