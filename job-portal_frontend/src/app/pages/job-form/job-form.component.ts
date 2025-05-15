import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { JobService } from '../../services/job.service';

@Component({
  selector: 'app-job-form',
  templateUrl: './job-form.component.html',
})
export class JobFormComponent implements OnInit {
  job: any = {};
  jobId?: number;

  constructor(private route: ActivatedRoute, private jobService: JobService, private router: Router) {}

  ngOnInit(): void {
    this.jobId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.jobId) {
      this.jobService.getJobById(this.jobId).subscribe(res => this.job = res.data);
    }
  }

  submit() {
    const req = this.jobId
      ? this.jobService.updateJob(this.jobId, this.job)
      : this.jobService.createJob(this.job);
    req.subscribe(() => this.router.navigate(['/recruiter']));
  }
}
