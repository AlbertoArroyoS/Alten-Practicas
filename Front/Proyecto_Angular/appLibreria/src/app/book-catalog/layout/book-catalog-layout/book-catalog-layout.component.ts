import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/auth/login.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Component({
  selector: 'app-book-catalog-layout',
  templateUrl: './book-catalog-layout.component.html',
  styleUrls: ['./book-catalog-layout.component.scss']
})
export class BookCatalogLayoutComponent {

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


}
