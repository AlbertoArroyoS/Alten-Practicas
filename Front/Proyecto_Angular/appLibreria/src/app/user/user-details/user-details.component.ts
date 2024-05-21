import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from './../../services/users/user.service';
import { LoginService } from 'src/app/services/auth/login.service';
import { UserRequest } from 'src/app/shared/model/request/userRequest';
import { Observable } from 'rxjs';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.scss']
})
export class UserDetailsComponent implements OnInit {

  userLoginOn$: Observable<boolean>;
  user$: Observable<AuthResponse | null>;
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
    // Asigna los observables del LoginService a las propiedades del componente
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;

    // Inicializa el formulario
    this.formularioUsuario = this.fb.group({
      idUsuario: [''],
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
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
    // Suscribirse a los cambios en los datos del usuario y parchear el formulario cuando los datos estén disponibles
    this.user$.subscribe({
      next: (userData) => {
        if (userData) {
          this.formularioUsuario.patchValue(userData);
          this.loadUserData(userData.idUsuario);
        }
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar datos del usuario.';
        console.error(error);
      }
    });

    // Cargar datos del usuario si ya está logueado
    const userId = this.loginService.getUserId();
    if (userId) {
      this.loadUserData(userId);
    }
  }

  /**
   * Carga los datos del usuario desde el servicio de usuario.
   * @param userId El ID del usuario.
   */
  loadUserData(userId: number): void {
    this.userService.getUser(userId).subscribe({
      next: (userData: UserRequest) => { // Asegúrate de que el tipo es UserRequest
        this.userData = userData;
        this.idControl = this.userData?.idUsuario;
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

  /**
   * Guarda los datos del usuario actualizados.
   */
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
