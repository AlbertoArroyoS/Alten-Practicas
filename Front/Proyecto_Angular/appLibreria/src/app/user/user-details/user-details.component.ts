import { UserService } from './../../services/users/user.service';
import { Component } from '@angular/core';
import { LoginService } from 'src/app/services/auth/login.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent {

  userLoginOn:boolean=true;
  userData?:UserRequest;
  errorMessage:String="";


  constructor(private UserService:UserService) { 

    this.UserService.getUser(4).subscribe({
      next: (userData) => {
        this.userData=userData;
      },
      error: (errorData) => {
        this.errorMessage=errorData
      },
      complete: () => {
        console.info("User Data ok");
      }
    })
  }



}
