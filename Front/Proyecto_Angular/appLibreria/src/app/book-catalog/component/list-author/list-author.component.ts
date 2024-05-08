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
  guardadoExitoso: boolean = false;
  alertaConflicto: boolean = false;
  successMessage: string = 'Autor guardado correctamente';
  warningMessage: string = '';
  formularioAutor: FormGroup;
  botonNuevoAutorVisible: boolean = false;
  eliminadoExitoso: boolean = false;
  successAlert: string = '';
  modificarAutor: boolean = false;
  nombreAutorEditar: string = '';
  mostrarBotonGuardar: boolean = true;
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
    this.formularioAutor = this.fb.group({
      id: [''],
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    this.title = 'Lista de autores';
    this.cargarTablaAutores();
  }

  cargarTablaAutores() {
    this.authorsService.getAllAuthors().subscribe(
      (response) => {
        this.autores = response.content;
      },
      (error) => {
        console.error('Error al cargar la tabla de autores:', error);
      }
    );
  }

  recargarTablaAutores() {
    this.cargarTablaAutores();
  }

  buscarAutores(keyword: string) {
    this.authorsService.searchAuthorsByKeyword(keyword).subscribe(
      (response) => {
        this.autores = response;
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
        this.book = response;
      },
      (error) => {
        console.error('Error al obtener el libro:', error);
        this.alertaConflicto = true;
        this.showWarningAlert('Conflicto al obtener el autor.');
      }
    );
  }

  guardarAutor() {
    this.authorsService.addAuthor(this.formularioAutor.value).subscribe(
      (resp) => {
        this.botonNuevoAutorVisible = false;
        this.guardadoExitoso = true;
        this.formularioAutor.reset();
        this.showSuccessAlert('Autor guardado correctamente');
        setTimeout(() => {
          this.guardadoExitoso = false;
        }, 3000);
        this.cargarTablaAutores();
      },
      (error: HttpErrorResponse) => {
        if (error.status === 409) {
          this.showWarningAlert(
            'Conflicto al guardar el autor. El autor ya existe.'
          );
          setTimeout(() => {
            this.alertaConflicto = false;
          }, 3000);
        } else {
          console.error(error);
          this.alertaConflicto = true;
          this.showWarningAlert('Conflicto al guardar el autor.');
        }
      }
    );
  }

  eliminarAutor(autor: any) {
    this.authorsService.deleteAuthorById(autor.id).subscribe(
      (resp) => {
        this.eliminadoExitoso = true;
        this.recargarTablaAutores();
        this.showSuccessAlert('Autor eliminado correctamente');
        setTimeout(() => {
          this.eliminadoExitoso = false;
        }, 3000);
      },
      (error) => {
        console.error('Error al eliminar el autor:', error);
        this.alertaConflicto = true;
        this.showWarningAlert(
          'Conflicto al eliminar el autor. El autor está asociado.'
        );
      }
    );
  }

  editarAutor(autor: any) {
    this.botonNuevoAutorVisible = true;
    this.modificarAutor = true;
    this.nombreAutorEditar = autor.nombre;
    this.mostrarBotonGuardar = false;
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
            this.formularioAutor.reset();
            this.autores = resp;
            this.showSuccessAlert('Autor actualizado correctamente');
            setTimeout(() => {
              this.guardadoExitoso = false;
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

  showSuccessAlert(message: string) {
    this.successMessage = message;
  }

  showWarningAlert(message: string) {
    this.warningMessage = message;
    this.alertaConflicto = true;
    setTimeout(() => {
      this.alertaConflicto = false;
    }, 4000);
  }

  botonNuevoAutor() {
    this.formularioAutor.reset();
    this.mostrarBotonGuardar = true;
    this.botonNuevoAutorVisible = !this.botonNuevoAutorVisible;
    this.modificarAutor = false;
  }

  getArrayOfPageNumbers(totalPages: number): number[] {
    return Array.from({ length: totalPages }, (_, i) => i);
  }
}
