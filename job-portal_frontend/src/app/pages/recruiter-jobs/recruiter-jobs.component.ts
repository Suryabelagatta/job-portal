import { Component, OnInit } from '@angular/core';
import { JobService } from '../../services/job.service';

@Component({
  selector: 'app-recruiter-jobs',
  templateUrl: './recruiter-jobs.component.html',
})
export class RecruiterJobsComponent implements OnInit {
  jobs: any[] = [];

  constructor(private jobService: JobService) {}

  ngOnInit(): void {
    this.jobService.getRecruiterJobs().subscribe(res => this.jobs = res.data);
  }

  delete(id: number) {
    if (confirm('Are you sure?')) {
      this.jobService.deleteJob(id).subscribe(() => {
        this.jobs = this.jobs.filter(j => j.id !== id);
      });
    }
  }
}
