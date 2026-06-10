import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CreateProjectRequest {
  name: string;
  description: string;
  projectStatus?: string;
}

export interface ProjectResponse {
  id: number;
  name: string;
  description: string;
  projectStatus: string;
}

@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  createProject(name: string, description: string): Observable<ProjectResponse> {
    const request: CreateProjectRequest = {
      name,
      description,
      projectStatus: 'ACTIVE'
    };
    return this.http.post<ProjectResponse>(`${this.apiUrl}/users/projects`, request);
  }
}
