import { LoginService } from './../../../services/auth/login.service';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {
  loginForm: FormGroup;
  loginError: string = "";
  passwordFieldType: string = 'password';

  constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService) {
    this.loginForm = this.formBuilder.group({
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required])
    });
  }

  get username() {
    return this.loginForm.controls['username'];
  }

  get password() {
    return this.loginForm.controls['password'];
  }

  /**
   * Método de login
   * Llama al servicio de login y maneja la respuesta.
   */
  login() {
    if (this.loginForm.valid) {
      this.loginService.loginSpring(this.loginForm.value as LoginRequest).subscribe({
        next: () => {
          this.router.navigateByUrl('/dashboard');
          this.loginForm.reset();
          window.scrollTo({ top: 0, behavior: 'smooth' });
        },
        error: (errorData) => {
          console.error('Error de login:', errorData);
          this.loginError = "Error al iniciar sesión. Por favor, inténtelo de nuevo.";
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }

  togglePasswordVisibility() {
    this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
  }
}
