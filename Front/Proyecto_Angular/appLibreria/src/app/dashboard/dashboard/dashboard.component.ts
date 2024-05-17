import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/auth/login.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  userLoginOn: boolean = false;

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
    this.loginService.userLoginOn.subscribe({
      next: (loginOn) => {
        this.userLoginOn = loginOn;
      }
    });
  }
}