import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },   // ← arranca en login

  {
    path: 'login',
    loadComponent: () => import('./components/login/login.component')
      .then(m => m.LoginComponent)
  },
  {
    path: 'registro',
    loadComponent: () => import('./components/registro/registro.component')
      .then(m => m.RegistroComponent)
  },
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

  { path: '**', redirectTo: 'login' }   // ← desconocido va a login
];