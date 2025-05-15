import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent implements OnInit {
  role: string | null = '';

  ngOnInit() {
    this.role = localStorage.getItem('role');
  }
}
