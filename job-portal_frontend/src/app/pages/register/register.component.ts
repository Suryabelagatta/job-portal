import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { RegisterRequest, Role } from '../../models/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: RegisterRequest = {
    fullName: '',
    email: '',
    password: '',
    phone: '',
    location: '',
    skills: '',
    bio: '',
    role: 'JOB_SEEKER'
  };

  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    this.authService.register(this.user).subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.error = 'Registration failed. Please check your input.'
    });
  }
}
