import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResumeService {
  private apiUrl = 'http://localhost:8080/api/resumes';

  constructor(private http: HttpClient) {}

  uploadResume(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(this.apiUrl, formData);
  }

  getUserResumes(): Observable<any> {
    return this.http.get(this.apiUrl);
  }

  deleteResume(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
getResumeMeta(resumeId: number) {
  return this.http.get<any>(`${this.apiUrl}/${resumeId}`);
}
downloadResume(resumeId: number| null) {
  return this.http.get<any>(`${this.apiUrl}/${resumeId}`);
}

}
