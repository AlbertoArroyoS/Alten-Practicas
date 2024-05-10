import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthorsService } from 'src/app/services/authors/authors.service';
import { BooksService } from 'src/app/services/books/books.service';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

// Decorador del componente
@Component({
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.scss'],
})
// Clase del componente
export class ListBookComponent {
  // Declaración de variables
  public title!: string;
  book: any;
  tableData: any; // Variable para controlar qué conjunto de datos usar en la tabla
  formularioLibro: FormGroup;
  autores: any;
  botonNuevoLibroVisible: boolean = false;
  books!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  // @ViewChild es un decorador que se utiliza en Angular para obtener una referencia a un elemento del DOM o a un componente hijo.
  @ViewChild('content', { static: true }) modalContent!: ElementRef;
  modificarLibro: boolean = false;
  mostrarBotonGuardar: boolean = true;
  alertaConflicto: boolean = false;
  successMessage: string = 'Libro guardado correctamente';
  warningMessage: string = '';
  eliminadoExitoso: boolean = false;
  guardadoExitoso: boolean = false;

  // Constructor de la clase
  constructor(
    public fb: FormBuilder,
    public booksService: BooksService,
    public authorsService: AuthorsService,
    private modalService: NgbModal
  ) {
    // Inicialización del formulario
    this.formularioLibro = this.fb.group({
      id: [''],
      titulo: new FormControl('', Validators.required),
      nombreAutor: new FormControl('', Validators.required),
      apellidosAutor: new FormControl('', Validators.required),
      genero: new FormControl('', Validators.required),
      paginas: new FormControl('', [Validators.required, Validators.min(1)]),
      editorial: new FormControl('', Validators.required),
      descripcion: new FormControl('', Validators.required),
    });
  }

  // Método que se ejecuta al inicializar el componente
  ngOnInit(): void {
    this.title = 'Lista de libros';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaLibros(0, 10);
  }

  // Método para cargar la tabla de libros
  cargarTablaLibros(page: number, size: number) {
    // Llamar al servicio para obtener la lista de libros
    this.booksService.getAllBooks(page, size).subscribe((data) => {
      this.books = Array.isArray(data.content) ? data.content : [];
      this.totalPaginas = Array.from(
        { length: data.totalPages },
        (_, i) => i + 1
      );
      this.currentPage = data.number;
    });
  }

  // Método para recargar la tabla de libros
  recargarTablaLibros() {
    // Llamar al método de cargar tabla de autores para volver a cargar los datos
    this.cargarTablaLibros(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  // Método para buscar libros
  buscarLibros(keyword: string) {
    // Llamar al servicio para buscar libros por palabra clave
    this.booksService.searchBooksByKeyword(keyword).subscribe((data) => {
      this.books = Array.isArray(data.content) ? data.content : [];
      //Cuando busco por keyWord, la respuesta viene en un objeto con la propiedad content que es un array de libros
      this.tableData = this.books; // Cambiar al conjunto de datos de la búsqueda
    });
  }

  // Método para guardar un libro
  guardarLibro() {
    // Validar el formulario antes de enviarlo
    if (this.formularioLibro.valid) {
      // Llamar al servicio para guardar el libro
      this.booksService.addBook(this.formularioLibro.value).subscribe({
        next: (resp) => {
          console.log('Libro guardado:', resp);
          this.botonNuevoLibroVisible = false;
          this.recargarTablaLibros();
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error al guardar el libro:', error);
          this.alertaConflicto = true;
          this.showWarningAlert('Conflicto al guardar el libro.');
        },
      });
    } else {
      console.error('El formulario no está completo');
      this.alertaConflicto = true;
      this.showWarningAlert('El formulario no está completo');
      Object.keys(this.formularioLibro.controls).forEach((field) => {
        // {1}
        const control = this.formularioLibro.get(field); // {2}
        //control.markAsTouched({ onlySelf: true });                  // {3}
      });
    }
  }

  // Método para mostrar el formulario de nuevo libro
  botonNuevoLibro() {
    this.formularioLibro.reset();
    this.botonNuevoLibroVisible = !this.botonNuevoLibroVisible;
  }

  // Método para obtener un libro por su ID
  obtenerLibroPorId(bookId: number) {
    this.booksService.getBookById(bookId).subscribe(
      (response) => {
        this.book = response; // Asigna el libro obtenido a una variable local
        this.abrirModal(this.book); // Llama al método openModal con el libro
      },
      (error) => {
        console.error('Error al obtener el libro:', error);
        this.alertaConflicto = true;
          this.showWarningAlert('Conflicto al obtener el libro.');
      }
    );
  }

  // Método para abrir el modal
  abrirModal(book: any) {
    this.book = book; // Asigna el libro a la variable de la clase para que esté disponible en el template
    this.modalService
      .open(this.modalContent, { ariaLabelledBy: 'modal-basic-title' })
      .result.then(
        (result) => {
          // Manejar el resultado si es necesario
        },
        (reason) => {
          // Manejar el cierre del modal si es necesario
        }
      );
  }

  // Método para editar un libro
  editarLibro(book: any) {
    this.botonNuevoLibroVisible = true;
    this.modificarLibro = true;
    //this.nombreLibroEditar = libro.titulo;
    this.mostrarBotonGuardar = false;
    this.formularioLibro.patchValue({
      id: book.id,
      titulo: book.titulo,
      nombreAutor: book.nombreAutor,
      apellidosAutor: book.apellidosAutor,
      genero: book.genero,
      paginas: book.paginas,
      editorial: book.editorial,
      descripcion: book.descripcion,
    });
  }

  // Método para actualizar un autor
  actualizarAutor() {
    const idControl = this.formularioLibro.get('id');
    if (idControl) {
      const libroId = idControl.value;
      this.booksService
        .updateBook(libroId, this.formularioLibro.value)
        .subscribe(
          (resp) => {
            this.guardadoExitoso = true;
            this.botonNuevoLibroVisible = false;
            this.formularioLibro.reset();
            const index = this.books.findIndex((a) => a.id === libroId);
            if (index !== -1) this.books[index] = resp; // Actualiza solo el autor modificado
            this.showSuccessAlert('Autor actualizado correctamente');
            setTimeout(() => {
              this.guardadoExitoso = false;
            }, 3000);
            this.recargarTablaLibros();
          },
          (error: HttpErrorResponse) => {
            this.alertaConflicto = true;
            this.showWarningAlert(
              'Conflicto al actualizar el libro. El libro ya existe.'
            );
          }
        );
    }
  }

  // Método para eliminar un libro
  eliminarLibro(books: any) {
    this.booksService.deleteBookById(books.id).subscribe(
      (resp) => {
        this.eliminadoExitoso = true;
        this.recargarTablaLibros();
        this.showSuccessAlert('Libro eliminado correctamente');
        setTimeout(() => {
          this.eliminadoExitoso = false;
        }, 3000);
      },
      (error) => {
        console.error('Error al eliminar el libro:', error);
        this.alertaConflicto = true;
        this.showWarningAlert(
          'Conflicto al eliminar el libro. El libro está asociado.'
        );
      }
    );
  }

  // Método para cambiar la página de la tabla
  changePage(pageNumber: number): void {
    this.cargarTablaLibros(pageNumber, this.pageSize);
  }

  // Método para cambiar el tamaño de la página de la tabla
  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement; // Asignación de tipo
    const size = Number(element.value); // Conversión de string a número
    this.pageSize = size;
    this.cargarTablaLibros(0, size);
  }

  // Método para mostrar una alerta de éxito
  showSuccessAlert(message: string) {
    this.successMessage = message;
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