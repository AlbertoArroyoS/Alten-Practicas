import { LoginService } from './../../../services/auth/login.service';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent {
  // Formulario de login
  loginForm: FormGroup;
  // Mensaje de error de login
  loginError: string = "";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private loginService: LoginService
  ) {
    // Inicializa el formulario de login con validaciones
    this.loginForm = this.formBuilder.group({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  // Getters para acceder fácilmente a los controles del formulario
  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  // Método de login
  login() {
    if (this.loginForm.valid) {
      // Llama al servicio de login y maneja la respuesta
      this.loginService.loginSpring(this.loginForm.value as LoginRequest).subscribe({
        next: (userData) => {
          console.log('Login exitoso:', userData);
          // Redirige al usuario a la página de dashboard después de un login exitoso
          this.router.navigateByUrl('/dashboard');
          // Resetea el formulario
          this.loginForm.reset();
          // Desplaza al principio de la página
          window.scrollTo({ top: 0, behavior: 'smooth' });
        },
        error: (errorData) => {
          console.error('Error de login:', errorData);
          // Muestra un mensaje de error en caso de fallo de login
          this.loginError = "Error al iniciar sesión. Por favor, inténtelo de nuevo.";
        },
        complete: () => {
          console.info("Login completo");
        }
      });
    } else {
      // Marca todos los controles del formulario como tocados para mostrar los errores de validación
      this.loginForm.markAllAsTouched();
    }
  }
}
