import { Component, OnInit } from '@angular/core';
import { JobService } from '../../services/job.service';

@Component({
  selector: 'app-job-search',
  templateUrl: './job-search.component.html',
})
export class JobSearchComponent implements OnInit {
  jobs: any[] = [];
  keyword = '';
  filters = { category: '', location: '', experience: '' };

  constructor(private jobService: JobService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.jobService.getAllJobs().subscribe(res => this.jobs = res.data);
  }

  search() {
    this.jobService.searchJobs(this.keyword).subscribe(res => this.jobs = res.data);
  }

  filter() {
    this.jobService.filterJobs(this.filters).subscribe(res => this.jobs = res.data);
  }
}
