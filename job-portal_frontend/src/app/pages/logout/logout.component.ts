import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-logout',
  template: `<div class="text-center mt-5"><p>Logging out...</p></div>`,
})
export class LogoutComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.logout();
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}
