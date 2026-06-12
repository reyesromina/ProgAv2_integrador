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
		loadComponent: () => import('./components/proyectos/create-project.component').then(m => m.CreateProjectComponent)
	},
	{ path: '**', redirectTo: 'login' }
];
