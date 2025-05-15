import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Application {
  id: number;
  jobId: number;
  job: {
    id: number;
    title: string;
    company: string;
    // Add other job fields as necessary
  };
  applicant: {
    id: number;
    fullName: string;
    email: string;
    // Add other applicant fields as necessary
  };
  coverLetter: string | null;
  resumeId: number | null;
  status: string;
  appliedDate: string;
}
@Injectable({
  providedIn: 'root'
})
export class ApplicationService {
  private apiUrl = 'http://localhost:8080/api/applications';

  constructor(private http: HttpClient) {}

applyToJob(payload: any): Observable<any> {
  return this.http.post('http://localhost:8080/api/applications', payload);
}


  getUserApplications(): Observable<any> {
    return this.http.get(`${this.apiUrl}/user`);
  }
getApplicationsByJob(jobId: number): Observable<{ success: boolean, message: string, data: Application[] }> {
  return this.http.get<{ success: boolean, message: string, data: Application[] }>(`${this.apiUrl}/job/${jobId}`);
}

  updateApplicationStatus(appId: number, status: string): Observable<any> {
    return this.http.put(`${this.apiUrl}/${appId}/status?status=${status}`, {});
  }
getAppliedJobIds(): Observable<any> {
  return this.http.get<number[]>(`${this.apiUrl}/user`);
}

  getApplicationById(applicationId: number): Observable<Application> {
    return this.http.get<Application>(`${this.apiUrl}/${applicationId}`);
  }

}
