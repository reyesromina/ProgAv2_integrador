import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export interface CreateProjectRequest {
  name: string;
  description: string;
   projectStatus: 'ACTIVE';
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
 
  private http = inject(HttpClient);

  createProject(name: string, description: string): Observable<ProjectResponse> {
    const request: CreateProjectRequest = {
      name,
      description,
       projectStatus: 'ACTIVE'
    };
    return this.http.post<ProjectResponse>(`${environment.apiUrl}/users/project`, request);
  }
}
