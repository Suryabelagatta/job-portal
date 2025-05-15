import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { RegisterComponent } from './pages/register/register.component';
import {ProfileComponent} from './pages/profile/profile.component';
import { JobListComponent } from './pages/job-list/job-list.component';
import { LogoutComponent } from './pages/logout/logout.component';
import { JobSearchComponent } from './pages/job-search/job-search.component';
import { RecruiterJobsComponent } from './pages/recruiter-jobs/recruiter-jobs.component';
import { JobDetailComponent } from './pages/job-detail/job-detail.component';
import { JobFormComponent } from './pages/job-form/job-form.component';
import { ApplyJobComponent } from './pages/apply-job/apply-job.component';
import { MyApplicationsComponent } from './pages/my-applications/my-applications.component';
import { ApplicationDetailComponent } from './pages/application-detail/application-detail.component';
import { RecruiterApplicationsComponent } from './pages/recruiter-applications/recruiter-applications.component';
import { RecruiterDashboardComponent } from './pages/recruiter-dashboard/recruiter-dashboard.component';



const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'jobs', component: JobListComponent },
  { path: 'logout',component: LogoutComponent},
  { path: 'jobs', component: JobSearchComponent },
  { path: 'jobs/:id', component: JobDetailComponent },
  { path: 'recruiter', component: RecruiterJobsComponent },
  { path: 'create-job', component: JobFormComponent },
  { path: 'edit-job/:id', component: JobFormComponent },
  { path: 'jobs/:id/apply', component: ApplyJobComponent },
  { path: 'applications/my', component: MyApplicationsComponent },
  { path: 'recruiter/jobs/:id/applications', component: RecruiterApplicationsComponent },
  { path: 'recruiter/applications/:id', component: ApplicationDetailComponent },
  { path: 'recruiter/dashboard', component: RecruiterDashboardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
