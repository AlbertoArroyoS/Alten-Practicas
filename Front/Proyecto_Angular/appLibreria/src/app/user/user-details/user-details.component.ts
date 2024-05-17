import { FormBuilder, FormGroup, Validators } from '@angular/forms';
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

  userLoginOn:boolean=false;
  editMode:boolean=false;
  userData?:UserRequest;
  errorMessage:String="";
  formularioUsuario: FormGroup;

  constructor(
    private userService: UserService,
    public fb: FormBuilder,
    private loginService: LoginService
  ) { 
    this.formularioUsuario = this.fb.group({
      username: ['', Validators.required],
      idUsuario: [{ value: '', disabled: true }],
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      enabled: ['', Validators.required],
      role: ['', Validators.required],
      idCliente: ['', Validators.required],
      idLibreria: ['', Validators.required],
      nombreLibreria: ['', Validators.required],
      nombreDueno: ['', Validators.required],
      direccion: ['', Validators.required],
      ciudad: ['', Validators.required]
    });

    this.userService.getUser(4).subscribe({
      next: (userData) => {
        this.userData = userData;
        this.formularioUsuario.patchValue(this.userData);
      },
      error: (errorData) => {
        this.errorMessage=errorData
      },
      complete: () => {
        console.info("User Data ok");
      }
    })

    this.loginService.userLoginOn.subscribe({
      next:(userLoginOn) => {
        this.userLoginOn=userLoginOn;
      }
    })
    
  }


  saveUserDetailsData(){

  }
}
