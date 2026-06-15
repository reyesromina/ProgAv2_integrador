
import { RegistroComponent } from './components/registro/registro.component';
import { LoginComponent } from './components/login/login.component';
import { StartComponent } from './components/start/start.component';
import { Routes } from '@angular/router';

export const routes: Routes = [
  // Ruta inicial → Start
  { path: '', redirectTo: 'start', pathMatch: 'full' },

  // Start (pantalla de bienvenida)
  {
    path: 'start',
    loadComponent: () => import('./components/start/start.component')
      .then(m => m.StartComponent)
  },

  // Auth
  {
    path: 'login',
    loadComponent: () => import('./components/login/login.component')
      .then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./components/registro/registro.component')
      .then(m => m.RegistroComponent)
  },

  // Proyectos
  {
    path: 'proyectos',
    loadComponent: () => import('./components/proyectos/proyect-list/proyectos-list.component')
      .then(m => m.ProyectosListComponent)
  },
  {
    path: 'proyectos/nuevo',
    loadComponent: () => import('./components/proyectos/create/create-project.component')
      .then(m => m.CreateProjectComponent)
  },
  {
    path: 'proyectos/:id',
    loadComponent: () => import('./components/proyectos/proyecto-detalle.component')
      .then(m => m.ProyectoDetalleComponent)
  },

  // Wildcard
  { path: '**', redirectTo: 'start' }
];