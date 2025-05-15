import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  loading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

login() {
  this.loading = true;
  this.error = '';

  this.authService.login({ email: this.email, password: this.password }).subscribe({
    next: (res) => {
      console.log('Login full response:', res);

      const token = res?.data?.token;
      const role = res?.data?.user?.role;

      if (token && token.split('.').length === 3) {
        this.authService.saveToken(res.data.token);  // <-- Ensure you're accessing `res.data.token`
        localStorage.setItem('role', role);
        this.router.navigate(['/']);
      } else {
        console.error('Invalid token received:', token);
        this.error = 'Login failed: Invalid token format.';
      }

      this.loading = false;
    },
    error: (err) => {
      console.error('Login error:', err);
      this.error = 'Invalid email or password.';
      this.loading = false;
    }
  });
}
}