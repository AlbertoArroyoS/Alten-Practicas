import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from './../../services/users/user.service';
import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/auth/login.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent {

  userLoginOn:boolean=false;
  editMode:boolean=false;
  userData!:UserRequest;
  errorMessage:String="";
  formularioUsuario: FormGroup;
  idControl!: number;

 constructor(
    private userService: UserService,
    public fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    this.formularioUsuario = this.fb.group({
      idUsuario: [''],
      username: new FormControl('', [Validators.required, Validators.email]),
      nombre: new FormControl('', Validators.required),
      apellidos: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
      enabled: new FormControl('', Validators.required),
      role: new FormControl('', Validators.required),
      idCliente: new FormControl('', Validators.required),
      idLibreria: new FormControl('', Validators.required),
      nombreLibreria: new FormControl('', Validators.required),
      nombreDueno: new FormControl('', Validators.required),
      direccion: new FormControl('', Validators.required),
      ciudad: new FormControl('', Validators.required),
    });
  }
  ngOnInit(): void {
    // Simular obtener el ID del usuario logueado de algún servicio de autenticación
    this.userService.getUser(4).subscribe({
      next: (userData) => {
        this.userData = userData;
        this.idControl = this.userData?.idUsuario ?? 0;  // Asignar valor por defecto si es undefined
        this.formularioUsuario.patchValue(this.userData);
      },
      error: (errorData) => {
        this.errorMessage = errorData;
      },
      complete: () => {
        console.info("User Data ok");
      }
    });

    this.loginService.userLoginOn.subscribe({
      next: (userLoginOn) => {
        this.userLoginOn = userLoginOn;
      }
    });
  }


  saveUserDetailsData(){

    if (this.formularioUsuario.valid)
      {
        if (this.idControl) {

          this.userService.updateUser(this.idControl,this.formularioUsuario.value).subscribe({
            next:() => {
              this.editMode=false;
              //this.userData=this.formularioUsuario.value as unknown as userData;
              this.userData=this.formularioUsuario.value;
            },
            error:(errorData)=> console.error(errorData)
          })
  
        }
        
      }
    }
}
