// import { Component, OnInit } from '@angular/core';
// import { JobService } from '../../services/job-list.service';
// import { Job } from '../../models/job.model';

// @Component({
//   selector: 'app-job-list',
//   templateUrl: './job-list.component.html',
//   styleUrls: ['./job-list.component.css']
// })
// export class JobListComponent implements OnInit {
//   jobs: Job[] = [];
//   error = '';

//   constructor(private jobService: JobService) {}

//   ngOnInit(): void {
//     this.jobService.getAllJobs().subscribe({
//       next: (data) => this.jobs = data,
//       error: () => this.error = 'Failed to load jobs.'
//     });
//   }
// }
import { Component, OnInit } from '@angular/core';
import { JobService } from '../../services/job-list.service';
import { ApplicationService } from '../../services/application.service';
import { Job } from '../../models/job.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.css']
})
export class JobListComponent implements OnInit {
  jobs: Job[] = [];
  filteredJobs: Job[] = [];
  appliedJobIds: number[] = [];

  title = '';
  location = '';
  category = '';
  company = '';
  error = '';

  constructor(
    private jobService: JobService,
    private applicationService: ApplicationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadJobs();
    this.loadAppliedJobs();
  }

  loadJobs(): void {
    this.jobService.getAllJobs().subscribe({
      next: (data) => {
        this.jobs = data;
        this.filteredJobs = data;
      },
      error: () => this.error = 'Failed to load jobs.'
    });
  }

loadAppliedJobs(): void {
  this.applicationService.getAppliedJobIds().subscribe({
    next: (res) => {
      // Extract only the jobId from each application
      this.appliedJobIds = res.data.map((app: any) => app.jobId);
    },
    error: () => console.error('Failed to load applied job IDs.')
  });
}


  applyToJob(jobId: number): void {
    if (this.appliedJobIds.includes(jobId)) {
      alert('You have already applied to this job.');
      return;
    }
    else{
      this.router.navigate(['/jobs', jobId, 'apply']);
    }
  }

  filterJobs(): void {
    this.filteredJobs = this.jobs.filter(job =>
      job.title.toLowerCase().includes(this.title.toLowerCase()) &&
      job.location.toLowerCase().includes(this.location.toLowerCase()) &&
      job.category.toLowerCase().includes(this.category.toLowerCase()) &&
      job.company.toLowerCase().includes(this.company.toLowerCase())
    );
  }

  isApplied(jobId: number): boolean {
    return this.appliedJobIds.includes(jobId);
  }
}
