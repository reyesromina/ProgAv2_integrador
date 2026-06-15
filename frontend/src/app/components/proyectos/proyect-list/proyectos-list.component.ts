import { Component, OnInit, ChangeDetectionStrategy, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { ProjectService, ProjectResponse } from '../../../services/project.service';

@Component({
  selector: 'app-proyectos-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './proyectos-list.component.html',
  styleUrl: './proyectos-list.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ProyectosListComponent implements OnInit {
  private projectService = inject(ProjectService);
  private router = inject(Router);

  projects = signal<ProjectResponse[]>([]);
  isLoading = signal(false);
  error = signal<string | null>(null);
  hasUserId = signal(true);

  ngOnInit(): void {
    this.loadProjects();
  }

  private loadProjects(): void {
    const userId = localStorage.getItem('userId');

    if (!userId) {
      this.hasUserId.set(false);
      this.error.set('No se pudo identificar al usuario. Por favor, inicie sesión nuevamente.');
      return;
    }

    this.isLoading.set(true);
    this.error.set(null);

    this.projectService.getProjects(Number(userId)).subscribe({
      next: (projects) => {
        this.projects.set(projects);
        this.isLoading.set(false);
      },
      error: (err) => {
        this.isLoading.set(false);
        this.error.set('Error al cargar los proyectos: ' + (err.error?.message || 'Error desconocido'));
      }
    });
  }

  navigateToProjectDetail(projectId: number): void {
    this.router.navigate(['/proyectos', projectId]);
  }

  navigateToCreateProject(): void {
    this.router.navigate(['/proyectos/nuevo']);
  }
}
