import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = 'http://localhost:8080/api/jobs';

@Injectable({ providedIn: 'root' })
export class JobService {
  constructor(private http: HttpClient) {}

  getAllJobs(): Observable<any> {
    return this.http.get(`${BASE_URL}`);
  }

  searchJobs(keyword: string): Observable<any> {
    return this.http.get(`${BASE_URL}/search`, { params: { keyword } });
  }

  filterJobs(filters: { category?: string, location?: string, experience?: string }): Observable<any> {
    let params = new HttpParams();
    if (filters.category) params = params.set('category', filters.category);
    if (filters.location) params = params.set('location', filters.location);
    if (filters.experience) params = params.set('experience', filters.experience);
    return this.http.get(`${BASE_URL}/filter`, { params });
  }

  getRecruiterJobs(): Observable<any> {
    return this.http.get(`${BASE_URL}/recruiter`);
  }

  getJobById(id: number): Observable<any> {
    return this.http.get(`${BASE_URL}/${id}`);
  }

  createJob(job: any): Observable<any> {
    return this.http.post(`${BASE_URL}`, job);
  }

  updateJob(id: number, job: any): Observable<any> {
    return this.http.put(`${BASE_URL}/${id}`, job);
  }

  deleteJob(id: number): Observable<any> {
    return this.http.delete(`${BASE_URL}/${id}`);
  }
    getJobsByRecruiter(): Observable<any> {
    return this.http.get(`${BASE_URL}/recruiter`);
  }
}
