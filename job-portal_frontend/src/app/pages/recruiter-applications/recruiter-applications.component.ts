// src/app/pages/recruiter-applications/recruiter-applications.component.ts
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApplicationService } from '../../services/application.service';
import { ResumeService } from '../../services/resume.service';

@Component({
  selector: 'app-recruiter-applications',
  templateUrl: './recruiter-applications.component.html'
})
export class RecruiterApplicationsComponent implements OnInit {
  jobId!: number;
  applications: any[] = [];

  constructor(private route: ActivatedRoute, private appService: ApplicationService, public resumeService :ResumeService) {}

  ngOnInit(): void {
    this.jobId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchApplications();
  }
//add .data after res in 23 line
  fetchApplications(): void {
    this.appService.getApplicationsByJob(this.jobId).subscribe({
      next: (res) => {this.applications = res.data
        console.log(this.applications);
      },
      error: (err) => console.error('Failed to fetch applications', err),
    });
  }

  updateStatus(appId: number, event: Event): void {
    const newStatus = (event.target as HTMLSelectElement).value;
    this.appService.updateApplicationStatus(appId, newStatus).subscribe({
      next: (res) => {
        const index = this.applications.findIndex(app => app.id === appId);
        if (index !== -1) {
          this.applications[index].status = newStatus;
        }
        alert('Status updated successfully!');
      },
      error: (err) => alert('Failed to update status'),
    });
  }
  viewResume(resumeId: number | null): void {
  if (!resumeId) {
    alert('Resume not available.');
    return;
  }

  this.resumeService.getResumeMeta(resumeId).subscribe({
    next: (res) => {
      const filePath = res?.data?.filePath;
      if (filePath) {
        // // Optional: convert absolute path to file URL (for dev only)
        // const fileUrl = `file://${filePath}`;
const accessibleUrl= `http://localhost:8080/uploads/${filePath.split('/uploads/')[1]}`;
window.open(accessibleUrl, '_blank');


        // const accessibleUrl = `${filePath}`;

        // window.open(accessibleUrl, '_blank');
      } else {
        alert('File path not found.');
      }
    },
    error: (err) => {
      console.error('Failed to fetch resume metadata', err);
      alert('Could not fetch resume.');
    }
  });
}

}
