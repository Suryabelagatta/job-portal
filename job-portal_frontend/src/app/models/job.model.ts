// src/app/models/job.model.ts
export interface Recruiter {
  id: number;
  email: string;
  fullName: string;
  role?: string;
  phone?: string;
  location?: string;
  skills?: string;
  bio?: string;
  createdAt?: string;
  updatedAt?: string;
}

export interface Job {
  id: number;
  title: string;
  description: string;
  company: string;
  location: string;
  category: string;
  experienceRequired: string;
  salary: string;
  recruiter: Recruiter;
  postedDate: string;
  status: string;
}
