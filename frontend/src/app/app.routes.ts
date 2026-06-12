import { Routes } from '@angular/router';


import { RegistroComponent } from './components/registro/registro.component';
import { LoginComponent } from './components/login/login.component';
import { StartComponent } from './components/start/start.component';

export const routes: Routes = [
{ 
    path: '', 
    component: StartComponent
  },
  { 
    path: 'login', 
    component: LoginComponent 
  },
  { 
    path: 'register', 
    component: RegistroComponent 
  },
	{
		path: 'proyectos',
		loadComponent: () => import('./components/proyectos/proyectos.component').then(m => m.ProyectosComponent)
	},
	{ path: '**', redirectTo: 'login' }
];
