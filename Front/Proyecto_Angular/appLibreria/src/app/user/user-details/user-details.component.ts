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
  userData?: UserRequest;
  errorMessage: string = '';
  formularioCliente: FormGroup;
  formularioUsuario: FormGroup;
  formularioLibreria: FormGroup;
  idUsuario!: number;
  idCliente!: number;
  idLibreria!: number;

  // Variables para mensajes de éxito y advertencia
  guardadoExitoso: boolean = false;
  alertaConflicto: boolean = false;
  successMessage: string = '';
  warningMessage: string = '';

  constructor(
    private userService: UserService,
    public fb: FormBuilder,
    private loginService: LoginService,
    private router: Router
  ) {
    // Asigna los observables del LoginService a las propiedades del componente
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;
    console.log('User login observable:', this.userLoginOn$);
    console.log('User observable:', this.user$);
    // Inicializa los formularios
    this.formularioUsuario = this.fb.group({
      idUsuario: [''],
      username: new FormControl({ value: '', disabled: true }),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
    });

    this.formularioCliente = this.fb.group({
      idCliente: new FormControl({ value: '', disabled: true }),
      nombre: new FormControl('', Validators.required),
      apellidos: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email]),
    });

    this.formularioLibreria = this.fb.group({
      idCliente: new FormControl({ value: '', disabled: true }),
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
          this.loadUserData(userData.idUsuario);
          console.log('Datos del usuario:', userData);
          console.log('user$:', this.user$);
        }
      },
      error: (error) => {
        this.errorMessage = 'Error al cargar datos del usuario.';
        console.error(error);
      }
    });

    // Cargar datos del usuario si ya está logueado
    const userId = this.loginService.getUserId();
    console.log('User ID:', userId);
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
      next: (userData: UserRequest) => { 
        this.userData = userData;
        this.idUsuario = userData.idUsuario;
        this.idCliente = userData.idCliente;
        this.idLibreria = userData.idLibreria;
        console.log('Datos del usuario en loadUserData: ', userData);

        // Parchear los formularios con los datos del usuario
        this.formularioUsuario.patchValue(userData);
        this.formularioCliente.patchValue(userData);
        this.formularioLibreria.patchValue(userData);
      },
      error: (errorData) => {
        this.errorMessage = errorData;
      }
    });
  }

  /**
   * Guarda los datos del usuario actualizados.
   */
  saveUserDetailsData() {
    if (this.formularioUsuario.valid) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.userService.updateUser(this.idUsuario, this.formularioUsuario.value).subscribe({
        next: () => {
          this.editMode = false;
          this.loadUserData(this.idUsuario);  // Recargar los datos del usuario después de guardar
          this.showSuccessAlert('Contraseña de acceso guardada correctamente.');
        },
        error: (errorData) => {
          console.error(errorData);
          this.showWarningAlert('Error al guardar los datos del usuario.');
        }
      });
    }
  }

  /**
   * Guarda los datos del cliente actualizados.
   */
  saveClientDetailsData() {
    if (this.formularioCliente.valid) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.userService.updateClient(this.idCliente, this.formularioCliente.value).subscribe({
        next: () => {
          this.editMode = false;
          this.loadUserData(this.idUsuario);  // Recargar los datos del usuario después de guardar
          this.showSuccessAlert('Datos del cliente guardados correctamente.');
        },
        error: (errorData) => {
          console.error(errorData);
          this.showWarningAlert('Error al guardar los datos del cliente. Ya existe un cliente con ese nombre.');
        }
      });
    }
  }

  /**
   * Guarda los datos de la librería actualizados.
   */
  saveLibraryDetailsData() {
    if (this.formularioLibreria.valid) {
      window.scrollTo({ top: 0, behavior: 'smooth' });
      this.userService.updateLibrary(this.idLibreria, this.formularioLibreria.value).subscribe({
        next: () => {
          this.editMode = false;
          this.loadUserData(this.idUsuario);  // Recargar los datos del usuario después de guardar
          this.showSuccessAlert('Datos de la librería guardados correctamente.');
        },
        error: (errorData) => {
          console.error(errorData);
          this.showWarningAlert('Error al guardar los datos de la librería. Nombre de libreria ya utilizado.');
        }
      });
    }
  }

  // Método para mostrar una alerta de éxito
  showSuccessAlert(message: string) {
    this.guardadoExitoso = true;
    this.successMessage = message;
    setTimeout(() => {
      this.guardadoExitoso = false;
    }, 4000);
  }

  // Método para mostrar una alerta de advertencia
  showWarningAlert(message: string) {
    this.warningMessage = message;
    this.alertaConflicto = true;
    setTimeout(() => {
      this.alertaConflicto = false;
    }, 4000);
  }
}
