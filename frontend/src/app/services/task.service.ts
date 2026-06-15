import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface TaskRequest {
  title: string;
  estimateHours: number;
  status: string;
  finishedAt: null;
}

export interface TaskResponse {
  id: number;
  title: string;
  estimateHours: number;
  status: string;
  createdAt: string;
  finishedAt: null;
  projectId: number;
}

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private readonly baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  createTask(projectId: number, task: TaskRequest): Observable<TaskResponse> {
    return this.http.post<TaskResponse>(
      `${this.baseUrl}/users/${projectId}/task`,
      task
    );
  }
}
