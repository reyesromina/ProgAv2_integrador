import { Routes } from '@angular/router';

export const routes: Routes = [
  { path: '', redirectTo: 'proyectos', pathMatch: 'full' },
  {
    path: 'proyectos',
    loadComponent: () => import('./components/proyectos/create-project.component')
      .then(m => m.CreateProjectComponent)
  },
  { path: '**', redirectTo: 'proyectos' }
];