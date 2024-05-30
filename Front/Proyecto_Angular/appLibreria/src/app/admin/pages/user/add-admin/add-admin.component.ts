import { Subscription } from 'rxjs';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/users/user.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.scss']
})
export class AddAdminComponent {

  formularioAdmin: FormGroup;
  successMessage: string = 'Autor guardado correctamente'; // Mensaje de éxito
  warningMessage: string = ''; // Mensaje de advertencia
  guardadoExitoso: boolean = false; // Indica si el autor se guardó correctamente
  alertaConflicto: boolean = false; // Indica si hubo un conflicto
  
  constructor(
    public fb: FormBuilder, // FormBuilder para crear formularios reactivos
    private userService: UserService, // Servicio de usuarios

  ) {
    // Inicialización del formulario
    this.formularioAdmin = this.fb.group({
      id: [''],
      username: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
      role: new FormControl('ADMIN'),
    });
  }

  // Método para guardar un autor
  guardarAdmin() {
    if (this.formularioAdmin.valid) {
      this.userService.addAdmin(this.formularioAdmin.value).subscribe(
        (resp) => {
          this.formularioAdmin.reset();
          this.showSuccessAlert('Usuario guardado correctamente');
        },
        (error: HttpErrorResponse) => {
          if (error.status === 409) {
            this.showWarningAlert('Conflicto al guardar el usuario. El usuario ya existe.');
          } else {
            this.showWarningAlert('Error al guardar el usuario.');
          }
        }
      );
    } else {
      this.formularioAdmin.markAllAsTouched();
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
