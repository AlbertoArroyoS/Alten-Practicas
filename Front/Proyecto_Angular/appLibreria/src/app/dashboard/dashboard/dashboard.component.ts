import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/auth/login.service';
import { Observable } from 'rxjs';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  // Observables para el estado de login y los datos del usuario
  userLoginOn$: Observable<boolean>;
  userData$: Observable<AuthResponse | null>;

  // Inyecta el LoginService en el constructor
  constructor(private loginService: LoginService) {
    // Asigna los observables del LoginService a las propiedades del componente
    this.userLoginOn$ = this.loginService.userLoginOn;
    this.userData$ = this.loginService.userData;
  }
}
