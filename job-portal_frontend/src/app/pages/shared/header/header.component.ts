import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';
import { User } from '../../../models/user.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  currentUser: User | null = null;
  
  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

ngOnInit(): void {
  this.authService.getCurrentUser().subscribe(user => {
    //console.log('Header received user:', user.data.role);
    this.currentUser = user.data;
  });
}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

  isJobSeeker(): boolean {
    return this.currentUser?.role === 'JOB_SEEKER';
  }

  isRecruiter(): boolean {
    return this.currentUser?.role === 'RECRUITER';
  }
}
