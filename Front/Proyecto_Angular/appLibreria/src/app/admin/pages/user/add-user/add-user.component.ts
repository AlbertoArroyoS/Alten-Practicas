import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/users/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent {

  formularioUsuario: FormGroup;
  successMessage: string = 'Usuario guardado correctamente'; // Mensaje de éxito
  warningMessage: string = ''; // Mensaje de advertencia
  guardadoExitoso: boolean = false; // Indica si el usuario se guardó correctamente
  alertaConflicto: boolean = false; // Indica si hubo un conflicto

  constructor(
    public fb: FormBuilder, // FormBuilder para crear formularios reactivos
    private userService: UserService // Servicio de usuarios
  ) { 
    this.formularioUsuario = this.fb.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
      nombre: ['', [Validators.required]],
      apellidos: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      nombreLibreria: ['', [Validators.required]],
      nombreDueno: ['', [Validators.required]],
      direccion: ['', [Validators.required]],
      ciudad: ['', [Validators.required]]
    });
  }


  // Método para guardar un usuario
  guardarUsuario() {
    if (this.formularioUsuario.valid) {
      this.userService.addUser(this.formularioUsuario.value).subscribe(
        (resp) => {
          this.formularioUsuario.reset();
          this.showSuccessAlert('Usuario guardado correctamente');
        },
        (error: HttpErrorResponse) => {
          if (error.status === 409) {
            this.showWarningAlert(
              'Conflicto al guardar el usuario. El usuario ya existe.'
            );
          } else {
            this.showWarningAlert('Conflicto al guardar el usuario.');
          }
        }
      );
      this.formularioUsuario.reset();
    } else {
      this.formularioUsuario.markAllAsTouched();
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
