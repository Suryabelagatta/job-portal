import { Component, OnInit } from '@angular/core';
import { ApplicationService } from '../../services/application.service';

@Component({
  selector: 'app-my-applications',
  templateUrl: './my-applications.component.html'
})
export class MyApplicationsComponent implements OnInit {
  applications: any[] = [];

  constructor(private appService: ApplicationService) {}

  ngOnInit(): void {
    this.appService.getUserApplications().subscribe({
      next: (res) => this.applications = res.data,
      error: (err) => console.error('Error fetching applications', err)
    });
  }
}
