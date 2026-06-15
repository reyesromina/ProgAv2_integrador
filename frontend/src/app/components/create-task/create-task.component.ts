import { Component, OnInit, ChangeDetectionStrategy, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TaskService } from '../../services/task.service';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-create-task',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-task.component.html',
  styleUrl: './create-task.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CreateTaskComponent implements OnInit {
  taskForm!: FormGroup;
  projectId!: number;
  isLoading = signal(false);

private readonly formBuilder = inject(FormBuilder);
private readonly taskService = inject(TaskService);
private readonly toastService = inject(ToastService);
private readonly router = inject(Router);
private readonly route = inject(ActivatedRoute);

  ngOnInit(): void {
    this.initializeForm();
    this.getProjectIdFromRoute();
  }

  private initializeForm(): void {
    this.taskForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100)]],
      estimateHours: ['', [Validators.required, Validators.min(1)]]
    });
  }

  private getProjectIdFromRoute(): void {
    this.route.params.subscribe(params => {
      this.projectId = +params['projectId'];
    });
  }

  onSubmit(): void {
    if (this.taskForm.invalid) {
      return;
    }

    this.isLoading.set(true);
    this.taskForm.disable();

    const taskData = {
      title: this.taskForm.get('title')?.value,
      estimateHours: this.taskForm.get('estimateHours')?.value,
      status: 'IN_PROGRESS',
      finishedAt: null
    };

    this.taskService.createTask(this.projectId, taskData).subscribe({
      next: (response) => {
        this.isLoading.set(false);
        this.toastService.showSuccess('Tarea creada exitosamente');
        this.taskForm.reset();
        this.router.navigate(['/proyectos']);
      },
      error: (error) => {
        this.isLoading.set(false);
        this.taskForm.enable();
        this.toastService.showError('Error al crear la tarea: ' + (error.error?.message || 'Error desconocido'));
      }
    });
  }

  get titleError(): string {
    const control = this.taskForm.get('title');
    if (control?.hasError('required')) {
      return 'El título es obligatorio';
    }
    if (control?.hasError('minlength')) {
      return 'El título debe tener al menos 3 caracteres';
    }
    if (control?.hasError('maxlength')) {
      return 'El título no puede exceder 100 caracteres';
    }
    return '';
  }

  get estimateHoursError(): string {
    const control = this.taskForm.get('estimateHours');
    if (control?.hasError('required')) {
      return 'Las horas estimadas son obligatorias';
    }
    if (control?.hasError('min')) {
      return 'Las horas deben ser mayores a 0';
    }
    return '';
  }

  get isTitleInvalid(): boolean {
    const control = this.taskForm.get('title');
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  get isEstimateHoursInvalid(): boolean {
    const control = this.taskForm.get('estimateHours');
    return !!(control && control.invalid && (control.dirty || control.touched));
  }

  goBack(): void {
    this.router.navigate(['/proyectos']);
  }
}
