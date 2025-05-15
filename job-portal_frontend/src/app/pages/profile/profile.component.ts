import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User = {
    fullName: '',
    email: '',
    phone: '',
    location: '',
    skills: '',
    bio: '',
    role: 'JOB_SEEKER'
  };

  editMode = false;
  message = '';
  error = '';

  constructor(private authService: AuthService) {}

ngOnInit(): void {
  this.authService.getCurrentUser().subscribe({
    next: (res) => {
      console.log('Response from /me:', res);
      this.user = res.data; // Extract the `data` field only
    },
    error: (err) => {
      console.error('Profile fetch failed', err);
      this.error = 'Failed to fetch profile.';
    }
  });
}


  enableEdit() {
    this.editMode = true;
    this.message = '';
    this.error = '';
  }

  saveProfile() {
    this.authService.updateUser(this.user).subscribe({
      next: (data) => {
        this.user = data;
        this.editMode = false;
        this.message = 'Profile updated successfully!';
      },
      error: () => this.error = 'Failed to update profile.'
    });
  }
}
