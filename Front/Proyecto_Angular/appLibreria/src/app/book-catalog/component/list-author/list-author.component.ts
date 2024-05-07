import { BooksService } from 'src/app/services/books/books.service';
import { AuthorsService } from './../../../services/authors/authors.service';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-list-author',
  templateUrl: './list-author.component.html',
  styleUrls: ['./list-author.component.scss'],
})
export class ListAuthorComponent {
  public title!: string;
  autores: any;
  book: any;
  guardadoExitoso: boolean = false; // Controla si el guardado fue exitoso
  alertaConflicto: boolean = false; // Controla si hay un conflicto en el guardado
  successMessage: string = 'Autor guardado correctamente'; // Mensaje de éxito
  warningMessage: string = 'Conflicto al guardar el autor. El autor ya existe.'; // Mensaje de advertencia
  formularioAutor: FormGroup; // Formulario para agregar autores
  botonNuevoAutorVisible: boolean = false; // Controla la visibilidad del botón de nuevo autor
  eliminadoExitoso: boolean = false;
  successAlert: string = ''; // Mensaje de éxito al eliminar un autor
  modificarAutor: boolean = false; // Controla si se está modificando un autor
  nombreAutorEditar: string = ''; // Nombre del autor que se está editando
  mostrarBotonGuardar: boolean = true; // Controla la visibilidad del botón de guardar
  //variables para paginacion
  currentPage: number = 0;
  pageSize: number = 10;
  field: string = 'id';
  order: number = 1;
  page: number = 0;

  constructor(
    public authorsService: AuthorsService,
    private booksService: BooksService,
    public fb: FormBuilder
  ) {
    // Inicializa el formulario con campos vacíos y validaciones
    this.formularioAutor = this.fb.group({
      id: [''],
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    this.title = 'Lista de autores';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaAutores();
  }

  cargarTablaAutores(

  ) {
    // Llamar al servicio para obtener la lista de autores con los parámetros de paginación y ordenamiento
    this.authorsService.getAllAuthors().subscribe(
      (response) => {
        this.autores = response.content; // Asignar los autores obtenidos a la variable del componente
      },
      (error) => {
        console.error('Error al cargar la tabla de autores:', error);
        this.alertaConflicto = true;
        this.showWarningAlert('Conflicto al cargar la tabla de autores.');
      }
    );
  }

  recargarTablaAutores() {
    // Llamar al método de cargar tabla de autores para volver a cargar los datos
    this.cargarTablaAutores();
  }

  buscarAutores(keyword: string) {
    // Llamar al servicio para buscar autores por palabra clave
    this.authorsService.searchAuthorsByKeyword(keyword).subscribe(
      (response) => {
        this.autores = response; // Actualizar la lista de autores con los resultados de la búsqueda
      },
      (error) => {
        console.error('Error al buscar autores:', error);
        this.alertaConflicto = true;
        this.showWarningAlert('Conflicto al buscar el autor.');
      }
    );
  }

  obtenerLibroPorId(bookId: number) {
    this.booksService.getBookById(bookId).subscribe(
      (response) => {
        this.book = response; // Asignar el libro obtenido a la variable del componente
      },
      (error) => {
        console.error('Error al obtener el libro:', error);
        this.alertaConflicto = true;
        this.showWarningAlert('Conflicto al obtener el autor.');
      }
    );
  }

  //Añadir un nuevo autor
  guardarAutor() {
    // Método para agregar un autor
    this.authorsService.addAuthor(this.formularioAutor.value).subscribe(
      (resp) => {
        // Si se añade el autor correctamente:
        this.botonNuevoAutorVisible = false;
        this.guardadoExitoso = true;
        this.formularioAutor.reset(); // Resetea el formulario
        this.showSuccessAlert('Autor guardado correctamente'); // Muestra una alerta de éxito
        setTimeout(() => {
          this.guardadoExitoso = false; // Desactiva la alerta de éxito después de 3 segundos
        }, 3000);
        // Recarga la tabla de autores después de agregar un nuevo autor
        this.cargarTablaAutores();
      },
      (error: HttpErrorResponse) => {
        // Si hay un error al añadir el autor:
        if (error.status === 409) {
          // Si el error es un conflicto (409):
          console.error('Error: Conflicto al guardar el autor'); // Muestra un mensaje de error en la consola
          this.showWarningAlert(
            'Conflicto al guardar el autor. El autor ya existe.'
          ); // Muestra una alerta de advertencia
          setTimeout(() => {
            this.alertaConflicto = false; // Desactiva la alerta de éxito después de 3 segundos
          }, 3000);
        } else {
          // Si es otro tipo de error:
          console.error(error); // Muestra el error en la consola
          this.alertaConflicto = true;
          this.showWarningAlert('Conflicto al guardar el autor.');
        }
      }
    );
  }

  eliminarAutor(autor: any) {
    // Método para eliminar un autor
    this.authorsService.deleteAuthorById(autor.id).subscribe(
      (resp) => {
        // Si se elimina el autor correctamente:
        this.eliminadoExitoso = true;
        this.recargarTablaAutores(); // Recarga la tabla de autores
        this.showSuccessAlert('Autor eliminado correctamente'); // Muestra una alerta de éxito
        setTimeout(() => {
          this.eliminadoExitoso = false; // Desactiva la alerta de éxito después de 3 segundos
        }, 3000);
      },
      (error) => {
        // Si hay un error al eliminar el autor:
        console.error('Error al eliminar el autor:', error); // Muestra el error en la consola
        this.alertaConflicto = true;
        this.showWarningAlert('Conflicto al eliminar el autor.');
      }
    );
  }

  editarAutor(autor: any) {
    this.botonNuevoAutorVisible = true;
    this.modificarAutor = true;
    this.nombreAutorEditar = autor.nombre;
    this.mostrarBotonGuardar = false;
    // Asignamos el id del autor que queremos editar
    this.formularioAutor.patchValue({
      id: autor.id,
    });
  }

  actualizarAutor() {
    const idControl = this.formularioAutor.get('id');
    if (idControl) {
      const autorId = idControl.value;
      this.authorsService
        .updateAuthor(autorId, this.formularioAutor.value)
        .subscribe(
          (resp) => {
            this.botonNuevoAutorVisible = false;
            this.formularioAutor.reset(); // Resetea el formulario
            this.autores = resp; // Actualiza la lista de autores
            this.showSuccessAlert('Autor actualizado correctamente'); // Muestra una alerta de éxito
            setTimeout(() => {
              this.guardadoExitoso = false; // Desactiva la alerta de éxito después de 3 segundos
            }, 3000);
            this.cargarTablaAutores();
          },
          (error: HttpErrorResponse) => {
            this.alertaConflicto = true;
            this.showWarningAlert(
              'Conflicto al guardar el autor. El autor ya existe.'
            );
          }
        );
    }
  }

  //******************************** */

  showSuccessAlert(message: string) {
    // Método para mostrar una alerta de éxito
    this.successMessage = message; // Establece el mensaje de éxito
    //this.guardadoExitoso = true; // Activa la alerta de éxito
  }

  showWarningAlert(message: string) {
    // Método para mostrar una alerta de advertencia
    this.warningMessage = message; // Establece el mensaje de advertencia
    this.alertaConflicto = true; // Activa la alerta de advertencia

    // Desactiva la alerta de advertencia después de 3 segundos
    setTimeout(() => {
      this.alertaConflicto = false;
    }, 3000);
  }

  botonNuevoAutor() {
    this.botonNuevoAutorVisible = !this.botonNuevoAutorVisible;
    this.mostrarBotonGuardar = true;
    this.modificarAutor = false;
    this.alertaConflicto = false;
  }

  //total paginas
  // En tu componente TypeScript
  getArrayOfPageNumbers(totalPages: number): number[] {
    return Array.from({ length: totalPages }, (_, i) => i);
  }

 
}
