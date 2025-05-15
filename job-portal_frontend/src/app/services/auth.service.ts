import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  LoginRequest,
  RegisterRequest,
  AuthResponse,
  User
} from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private authUrl = 'http://localhost:8080/api/auth';
  private userUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  // 🔐 AUTH METHODS

  login(data: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.authUrl}/login`, data);
  }

  register(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.authUrl}/register`, data);
  }

  saveToken(token: string): void {
    localStorage.setItem('authToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  logout(): void {
    localStorage.removeItem('authToken');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // 🧠 USER METHODS (Authenticated)

getAuthHeaders() {
  const token = this.getToken();
  return {
    headers: new HttpHeaders({
      Authorization: `Bearer ${token}`
    })
  };
}


getCurrentUser(): Observable<any> {
  return this.http.get('http://localhost:8080/api/users/me', this.getAuthHeaders());
}


updateUser(user: User): Observable<any> {
  return this.http.put('http://localhost:8080/api/users', user, this.getAuthHeaders());
}
}





// import { Injectable } from '@angular/core';
// import { Observable } from 'rxjs';
// import { AuthResponse, LoginRequest, RegisterRequest, User } from '../models/user.model';
// import { HttpClient, HttpHeaders } from '@angular/common/http';
// @Injectable({
//   providedIn: 'root'
// })
// export class AuthService {
//   private authUrl = 'http://localhost:8080/api/auth';
//   private userUrl = 'http://localhost:8080/api/users';

//   constructor(private http: HttpClient) {}

//   login(data: LoginRequest): Observable<AuthResponse> {
//     return this.http.post<AuthResponse>(`${this.authUrl}/login`, data);
//   }

//   register(data: RegisterRequest): Observable<any> {
//     return this.http.post(`${this.authUrl}/register`, data);
//   }

//   saveToken(token: string): void {
//     localStorage.setItem('authToken', token);
//   }

//   getToken(): string | null {
//     return localStorage.getItem('authToken');
//   }

//   logout(): void {
//     localStorage.removeItem('authToken');
//   }

//   isLoggedIn(): boolean {
//     return !!this.getToken();
//   }

//   getAuthHeaders() {
//     const token = this.getToken();
//     return {
//       headers: new HttpHeaders({
//         Authorization: `Bearer ${token}`
//       })
//     };
//   }

//   getCurrentUser(): Observable<User> {
//     return this.http.get<User>(`${this.userUrl}/me`, this.getAuthHeaders());
//   }

//   updateUser(user: User): Observable<any> {
//     return this.http.put(`${this.userUrl}`, user, this.getAuthHeaders());
//   }
// }