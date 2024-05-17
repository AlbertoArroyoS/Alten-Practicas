import { LoginService } from './../../../services/auth/login.service';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss'],
})
export class SignInComponent {
  loginForm: FormGroup;
  loginError:string="";

  constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService) {
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
    });
  }

  login() {
    if (this.loginForm.valid) {
      this.loginService.loginSpring(this.loginForm.value as LoginRequest).subscribe({
        next: (userData) => {
          console.log(userData);
          // Redirigir y resetear el formulario aquí para asegurar que solo ocurra en caso de éxito
          this.router.navigateByUrl('/dashboard');
          this.loginForm.reset();
          // Desplazar al principio de la página
          window.scrollTo({ top: 0, behavior: 'smooth' });
        },
        error: (errorData) => {
          console.error(errorData);
          this.loginError = errorData;
        },
        complete: () => {
          console.info("Login completo");
        }
      });
    } else {
      this.loginForm.markAllAsTouched();
      //alert('Formulario invalido');
    }
  }
  
}
