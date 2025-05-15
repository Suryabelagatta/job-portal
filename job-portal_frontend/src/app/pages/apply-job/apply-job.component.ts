import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApplicationService } from '../../services/application.service';
import { ResumeService } from '../../services/resume.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-apply-job',
  templateUrl: './apply-job.component.html'
})
export class ApplyJobComponent implements OnInit {
  applyForm: FormGroup;
  selectedResume: File | null = null;
  jobId!: number;
  successMessage = '';
  errorMessage = '';
  resumeId: number | null = null;



  constructor(
    private fb: FormBuilder,
    private appService: ApplicationService,
    private route: ActivatedRoute,
    private resumeService: ResumeService,
    private router: Router,
  ) {
this.applyForm = this.fb.group({
  coverLetter: ['', Validators.required],
  resume: [null, Validators.required],
  additionalInfo: ['']
});
  }

 ngOnInit(): void {
  const jobIdParam = this.route.snapshot.paramMap.get('id');
  if (jobIdParam) {
    this.jobId = +jobIdParam;
  } else {
    this.errorMessage = 'Invalid job ID';
  }
}

onFileChange(event: any): void {
  const file = event.target.files[0];
  if (file) {
    this.selectedResume = file;
    this.resumeService.uploadResume(file).subscribe({
      next: (res) => {
        this.resumeId = res.data.id;
        this.applyForm.get('resume')?.setValue(this.selectedResume); // ✅ fix
        this.errorMessage = '';
        console.log('Resume uploaded, ID:', this.resumeId);
      },
      error: (err) => {
        this.errorMessage = 'Resume upload failed';
        console.error(err);
      }
    });
  }
}



  onSubmit(): void {
    if (!this.applyForm.valid || !this.resumeId) {
      this.errorMessage = 'Please fill all required fields and upload resume first.';
      return;
    }

    const applicationPayload = {
      jobId: this.jobId,
      coverLetter: this.applyForm.get('coverLetter')?.value,
      additionalInfo: this.applyForm.get('additionalInfo')?.value,
      resumeId: this.resumeId
    };

    this.appService.applyToJob(applicationPayload).subscribe({
      next: (res) => {
      alert('Application submitted successfully!');
      this.applyForm.reset();
      this.resumeId = null;
      this.router.navigate(['/jobs']);
      },
      error: (err) => {
        this.errorMessage = err.error?.message || 'Application failed.';
        console.error('Application failed:', err);
      }
    });
  }
}
