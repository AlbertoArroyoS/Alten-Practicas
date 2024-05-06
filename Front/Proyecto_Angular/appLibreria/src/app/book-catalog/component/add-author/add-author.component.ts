import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthorsService } from 'src/app/services/authors/authors.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-author',
  templateUrl: './add-author.component.html',
  styleUrls: ['./add-author.component.scss'],
})
export class AddAuthorComponent implements OnInit {
  formularioAutor: FormGroup; // Formulario para agregar autores
  autores: any; // Lista de autores
  guardadoExitoso: boolean = false; // Controla si el guardado fue exitoso
  alertaConflicto: boolean = false; // Controla si hay un conflicto en el guardado
  successMessage: string = ''; // Mensaje de éxito
  warningMessage: string = ''; // Mensaje de advertencia

  constructor(public fb: FormBuilder, public authorsService: AuthorsService) {
    // Inicializa el formulario con campos vacíos y validaciones
    this.formularioAutor = this.fb.group({
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    // Método de Angular que se ejecuta al inicializar el componente
  }

  addAuthor() {
    // Método para agregar un autor
    this.authorsService.addAuthor(this.formularioAutor.value).subscribe(
      (resp) => {
        // Si se añade el autor correctamente:
        this.formularioAutor.reset(); // Resetea el formulario
        this.autores = resp; // Actualiza la lista de autores
        this.showSuccessAlert('Autor guardado correctamente'); // Muestra una alerta de éxito
        setTimeout(() => {
          this.guardadoExitoso = false; // Desactiva la alerta de éxito después de 3 segundos
        }, 3000);
      },
      (error: HttpErrorResponse) => {
        // Si hay un error al añadir el autor:
        if (error.status === 409) {
          // Si el error es un conflicto (409):
          console.error('Error: Conflicto al guardar el autor'); // Muestra un mensaje de error en la consola
          this.showWarningAlert('Conflicto al guardar el autor'); // Muestra una alerta de advertencia
          setTimeout(() => {
            this.alertaConflicto = false; // Desactiva la alerta de éxito después de 3 segundos
          }, 3000);
        } else {
          // Si es otro tipo de error:
          console.error(error); // Muestra el error en la consola
        }
      }
    );
  }

  showSuccessAlert(message: string) {
    // Método para mostrar una alerta de éxito
    this.successMessage = message; // Establece el mensaje de éxito
    this.guardadoExitoso = true; // Activa la alerta de éxito
  }

  showWarningAlert(message: string) {
    // Método para mostrar una alerta de advertencia
    this.warningMessage = message; // Establece el mensaje de advertencia
    this.alertaConflicto = true; // Activa la alerta de advertencia
  }
}
