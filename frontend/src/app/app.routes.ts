import { Routes } from '@angular/router';

export const routes: Routes = [

	{ path: '', redirectTo: 'login', pathMatch: 'full' },
     {
    path: 'registro',
    loadComponent: () => import('./components/registro/registro.component')
      .then(m => m.RegistroComponent)
  },
	{
		path: 'login',
		loadComponent: () => import('./components/login/login.component').then(m => m.LoginComponent)
	},
	{
		path: 'proyectos',
		loadComponent: () => import('./components/proyectos/proyectos.component').then(m => m.ProyectosComponent)
	},
	{
		path: 'proyectos/:projectId/crear-tarea',
		loadComponent: () => import('./components/create-task/create-task.component').then(m => m.CreateTaskComponent)
	},
	{ path: '**', redirectTo: 'login' }
];
