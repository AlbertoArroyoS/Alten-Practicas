import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from './../../services/users/user.service';
import { LoginService } from 'src/app/services/auth/login.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  userLoginOn: boolean = false;
  editMode: boolean = false;
  userData?: UserRequest; // Nota: Cambiado a UserRequest
  errorMessage: string = '';
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
    this.loginService.userData.subscribe({
      next: (userData) => {
        if (userData) {
          this.loadUserData(userData.idUsuario);
        }
      }
    });

    this.loginService.userLoginOn.subscribe({
      next: (userLoginOn) => {
        this.userLoginOn = userLoginOn;
      }
    });

    // Cargar datos del usuario si ya está logueado
    const userId = this.loginService.getUserId();
    if (userId) {
      this.loadUserData(userId);
    }
  }

  loadUserData(userId: number): void {
    this.userService.getUser(userId).subscribe({
      next: (userData: UserRequest) => { // Asegúrate de que el tipo es UserRequest
        this.userData = userData;
        this.idControl = this.userData?.idUsuario ?? 0;
        this.formularioUsuario.patchValue(this.userData);
      },
      error: (errorData) => {
        this.errorMessage = errorData;
      },
      complete: () => {
        console.info("User Data ok");
      }
    });
  }

  saveUserDetailsData() {
    if (this.formularioUsuario.valid) {
      if (this.idControl) {
        this.userService.updateUser(this.idControl, this.formularioUsuario.value).subscribe({
          next: () => {
            this.editMode = false;
            this.userData = this.formularioUsuario.value;
          },
          error: (errorData) => console.error(errorData)
        });
      }
    }
  }
}
