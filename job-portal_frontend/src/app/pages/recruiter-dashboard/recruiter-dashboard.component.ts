// src/app/pages/recruiter-dashboard/recruiter-dashboard.component.ts
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApplicationService } from '../../services/application.service';
import { JobService } from '../../services/job.service';


@Component({
  selector: 'app-recruiter-dashboard',
  templateUrl: './recruiter-dashboard.component.html',
})
export class RecruiterDashboardComponent implements OnInit {
  jobs: any[] = [];

  constructor(private applicationService: ApplicationService, private router: Router, private jobService: JobService) {}

  ngOnInit(): void {
    this.jobService.getJobsByRecruiter().subscribe({
      next: (res) => {
        this.jobs = res.data;
      },
      error: (err) => console.error('Error fetching recruiter jobs', err),
    });
  }

  viewApplicants(jobId: number): void {
    this.router.navigate(['/recruiter/jobs', jobId, 'applications']);
  }
}
