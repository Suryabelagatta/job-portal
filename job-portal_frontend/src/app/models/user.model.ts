export type Role = 'JOB_SEEKER' | 'RECRUITER';

export interface User {
  id?: number;
  email: string;
  password?: string;
  fullName: string;
  phone?: string;
  location?: string;
  skills?: string;
  bio?: string;
  role: Role;
}

export interface RegisterRequest {
  fullName: string;
  email: string;
  password: string;
  phone?: string;
  location?: string;
  skills?: string;
  bio?: string;
  role: Role;
}

export interface LoginRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  success: boolean;
  message: string;
  data: {
    token: string;
    user: {
      id: number;
      email: string;
      fullName: string;
      role: string;
      phone?: string;
      location?: string;
      skills?: string;
      bio?: string;
    };
  };
}
