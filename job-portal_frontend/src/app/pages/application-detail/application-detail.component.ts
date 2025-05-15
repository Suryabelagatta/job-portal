import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicationService, Application } from '../../services/application.service';
import { ResumeService } from '../../services/resume.service';

@Component({
  selector: 'app-application-detail',
  templateUrl: './application-detail.component.html',
  styleUrls: ['./application-detail.component.css']
})
export class ApplicationDetailComponent implements OnInit {
  applicationId!: number;
  application!: Application;
  statuses = ['PENDING', 'SHORTLISTED', 'REJECTED'];

  constructor(
    private route: ActivatedRoute,
    private applicationService: ApplicationService,
    public resumeService: ResumeService,
  ) {}

ngOnInit(): void {
  console.log('ApplicationDetailComponent initialized');
  this.applicationId = +this.route.snapshot.paramMap.get('id')!;
  console.log('Application ID from route:', this.applicationId);
  this.loadApplication();
}


loadApplication(): void {
  console.log('Calling applicationService.getApplicationById with id:', this.applicationId);
  this.applicationService.getApplicationById(this.applicationId).subscribe({
    next: (data) => {
      this.application = data;
      console.log('Application', this.application);
      console.log('Application Resume ID:', this.application.resumeId);
    },
    error: (err) => console.error('Error fetching application', err)
  });
}


updateStatus(applicationId: number, status: string): void {
  this.applicationService.updateApplicationStatus(applicationId, status)
    .subscribe({
      next: (res) => {
        this.application.status = res.data.status;
        console.log(this.application.status);
        alert('Status updated successfully!');
      },
      error: (err) => {
        console.error('Error updating status:', err);
      }
    });
}

  onStatusChange(event: Event): void {
  const selectedStatus = (event.target as HTMLSelectElement).value;
  if (this.application) {
    this.updateStatus(this.application.id, selectedStatus);
  }
}

}
