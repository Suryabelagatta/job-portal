import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './pages/login/login.component';
import { HomeComponent } from './pages/home/home.component';
import { AuthInterceptor } from './auth.interceptor';
import { RegisterComponent } from './pages/register/register.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { JobListComponent } from './pages/job-list/job-list.component';
import { LogoutComponent } from './pages/logout/logout.component';
import { JobSearchComponent } from './pages/job-search/job-search.component';
import { RecruiterJobsComponent } from './pages/recruiter-jobs/recruiter-jobs.component';
import { JobFormComponent } from './pages/job-form/job-form.component';
import { JobDetailComponent } from './pages/job-detail/job-detail.component';
import { HeaderComponent } from './pages/shared/header/header.component';
import { FooterComponent } from './pages/shared/footer/footer.component';
import { LoadingSpinnerComponent } from './pages/shared/loading-spinner/loading-spinner.component';
import { ApplyJobComponent } from './pages/apply-job/apply-job.component';
import { MyApplicationsComponent } from './pages/my-applications/my-applications.component';
import { RecruiterApplicationsComponent } from './pages/recruiter-applications/recruiter-applications.component';
import { ApplicationDetailComponent } from './pages/application-detail/application-detail.component';
import { RecruiterDashboardComponent } from './pages/recruiter-dashboard/recruiter-dashboard.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    RegisterComponent,
    ProfileComponent,
    JobListComponent,
    LogoutComponent,
    JobSearchComponent,
    RecruiterJobsComponent,
    JobFormComponent,
    JobDetailComponent,
    HeaderComponent,
    FooterComponent,
    LoadingSpinnerComponent,
    ApplyJobComponent,
    MyApplicationsComponent,
    RecruiterApplicationsComponent,
    ApplicationDetailComponent,
    RecruiterDashboardComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,  // ✅ Add this
    FormsModule 
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
    exports: [
    HeaderComponent,
    FooterComponent,
    LoadingSpinnerComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
