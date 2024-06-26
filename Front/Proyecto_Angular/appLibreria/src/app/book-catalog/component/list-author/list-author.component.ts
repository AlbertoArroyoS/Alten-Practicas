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
import { Subscription } from 'rxjs';
import { Router } from '@angular/router';

// Componente para listar autores
@Component({
  selector: 'app-list-author',
  templateUrl: './list-author.component.html',
  styleUrls: ['./list-author.component.scss'],
})
export class ListAuthorComponent {
  protected subscription: Array<Subscription> = new Array(); // Array de suscripciones

  public title!: string; // Título de la página
  book: any; // Libro seleccionado
  guardadoExitoso: boolean = false; // Indica si el autor se guardó correctamente
  alertaConflicto: boolean = false; // Indica si hubo un conflicto
  successMessage: string = 'Autor guardado correctamente'; // Mensaje de éxito
  warningMessage: string = ''; // Mensaje de advertencia
  formularioAutor: FormGroup; // Formulario para crear/editar autores
  botonNuevoAutorVisible: boolean = false; // Indica si el botón para crear un nuevo autor es visible
  eliminadoExitoso: boolean = false; // Indica si el autor se eliminó correctamente
  successAlert: string = ''; // Mensaje de alerta de éxito
  modificarAutor: boolean = false; // Indica si se está modificando un autor
  nombreAutorEditar: string = ''; // Nombre del autor a editar
  mostrarBotonGuardar: boolean = true; // Indica si el botón de guardar es visible
  autores!: any[]; // Lista de autores
  totalPaginas?: number[]; // Total de páginas
  currentPage: number = 0; // Página actual
  pageSize: number = 10; // Tamaño de la página
  paginacion: boolean = true; // Indica si se muestra la paginación

  // Constructor del componente
  constructor(
    public authorsService: AuthorsService, // Servicio de autores
    private booksService: BooksService, // Servicio de libros
    public fb: FormBuilder, // FormBuilder para crear formularios reactivos
    private router: Router // Router para navegar entre páginas
  ) {
    // Inicialización del formulario
    this.formularioAutor = this.fb.group({
      id: [''],
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
    });
  }

  // Método que se ejecuta al iniciar el componente
  ngOnInit(): void {
    this.title = 'Lista de autores';
    this.cargarTablaAutores(0, 10);
  }
  // Método que se ejecuta al destruir el componente y desuscribirse de las suscripciones
  ngOnDestroy(): void {
    // Desuscribirse de cada suscripción en el array de suscripciones
    this.subscription.forEach((subscription) => {
      subscription.unsubscribe();
    });
  }

  // Método para cargar la tabla de autores
  cargarTablaAutores(page: number, size: number) {
    this.subscription.push(
      this.authorsService.getAllAuthors(page, size).subscribe((data) => {
        this.autores = Array.isArray(data.content) ? data.content : [];
        this.totalPaginas = Array.from(
          { length: data.totalPages },
          (_, i) => i + 1
        );
        this.currentPage = data.number;
      })
    );
  }

  // Método para recargar la tabla de autores
  recargarTablaAutores(): void {
    this.cargarTablaAutores(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  // Método para buscar autores por palabra clave
  buscarAutores(keyword: string) {
    this.subscription.push(
      this.authorsService.searchAuthorsByKeyword(keyword).subscribe(
        (response) => {
          this.autores = Array.isArray(response) ? response : [];
          this.paginacion = false;
        },
        (error) => {
          this.showWarningAlert('Conflicto al buscar el autor.');
        }
      )
    );
  }

  // Método para obtener un libro por su ID
  obtenerLibroPorId(idAutor: number) {
    this.subscription.push(
      this.booksService.getBookById(idAutor).subscribe(
        (response) => {
          this.book = response;
        },
        (error) => {
          this.showWarningAlert('Conflicto al obtener el autor.');
        }
      )
    );
  }
  // Método para cargar los libros por autor
  obtenerLibrosPorIdAutor(authorId: number): void {
    this.router.navigate(['book-catalog/list-book/author/', authorId]);
  }

  // Método para guardar un autor
  guardarAutor() {
    if (this.formularioAutor.valid) {
      this.subscription.push();
      this.authorsService.addAuthor(this.formularioAutor.value).subscribe(
        (resp) => {
          this.botonNuevoAutorVisible = false;
          this.formularioAutor.reset();
          this.showSuccessAlert('Autor guardado correctamente');
          this.recargarTablaAutores();
        },
        (error: HttpErrorResponse) => {
          if (error.status === 409) {
            this.showWarningAlert(
              'Conflicto al guardar el autor. El autor ya existe.'
            );
          } else {
            this.showWarningAlert('Conflicto al guardar el autor.');
          }
        }
      );
      this.formularioAutor.reset();
    } else {
      this.formularioAutor.markAllAsTouched();
      //alert('Formulario invalido');
    }
  }

  // Método para eliminar un autor
  eliminarAutor(autor: any) {
    this.subscription.push(
      this.authorsService.deleteAuthorById(autor.id).subscribe(
        (resp) => {
          // Desplazar al principio de la página
          window.scrollTo({ top: 0, behavior: 'smooth' });
          this.recargarTablaAutores();
          this.showSuccessAlert('Autor eliminado correctamente');
        },
        (error) => {
          this.showWarningAlert(
            'Conflicto al eliminar el autor. El autor está asociado.'
          );
        }
      )
    );
  }

  // Método para editar un autor
  editarAutor(autor: any) {
    // Desplazar al principio de la página
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.botonNuevoAutorVisible = true;
    this.modificarAutor = true;
    this.nombreAutorEditar = autor.nombre;
    this.mostrarBotonGuardar = false;
    this.formularioAutor.patchValue({
      id: autor.id,
      nombre: autor.nombre,
      apellidos: autor.apellidos,
    });
  }

  // Método para actualizar un autor
  actualizarAutor() {
    const idControl = this.formularioAutor.get('id');
    if (idControl) {
      const autorId = idControl.value;
      this.subscription.push(
        this.authorsService
          .updateAuthor(autorId, this.formularioAutor.value)
          .subscribe(
            (resp) => {
              // Desplazar al principio de la página
              window.scrollTo({ top: 0, behavior: 'smooth' });
              this.modificarAutor = false;
              this.guardadoExitoso = true;
              this.botonNuevoAutorVisible = false;
              this.formularioAutor.reset();
              const index = this.autores.findIndex((a) => a.id === autorId);
              if (index !== -1) this.autores[index] = resp;
              this.showSuccessAlert('Autor actualizado correctamente');
              this.recargarTablaAutores();
            },
            (error: HttpErrorResponse) => {
              this.showWarningAlert(
                'Conflicto al guardar el autor.'
              );
            }
          )
      );
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

  // Método para mostrar el formulario de nuevo autor
  botonNuevoAutor() {
    this.formularioAutor.reset();
    this.mostrarBotonGuardar = true;
    this.botonNuevoAutorVisible = !this.botonNuevoAutorVisible;
    this.modificarAutor = false;
  }

  // Método para obtener un array de números de páginas
  getArrayOfPageNumbers(totalPages: number): number[] {
    return Array.from({ length: totalPages }, (_, i) => i);
  }

  // Método para cambiar de página
  changePage(pageNumber: number): void {
    this.cargarTablaAutores(pageNumber, this.pageSize);
  }

  // Método para cambiar el tamaño de la página
  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement;
    const size = Number(element.value);
    this.pageSize = size;
    this.cargarTablaAutores(0, size);
  }
}
