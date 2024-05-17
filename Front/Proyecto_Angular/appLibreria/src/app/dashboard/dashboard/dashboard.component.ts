import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/auth/login.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {

  userLoginOn:boolean=false;
  userData?:UserRequest;

  constructor(private loginService:LoginService) { }

  ngOnInit(): void {
    this.loginService.currentUserLoginOn.subscribe({
      //si el valor cambia, se actualiza la propiedad userLoginOn
      next:(userLoginOn) => {
        this.userLoginOn=userLoginOn;
      }
    });
    //suscribirse a los cambios en los datos del usuario
    this.loginService.currentUserData.subscribe({
      next:(userData)=>{
        this.userData=userData;
      }
    })
  }
  //Desuscribirse de los observables para evitar fugas de memoria
  ngOnDestroy(): void {
    this.loginService.currentUserData.unsubscribe();
    this.loginService.currentUserLoginOn.unsubscribe();
  }


}
