import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthorsService } from 'src/app/services/authors/authors.service';
import { BooksService } from 'src/app/services/books/books.service';

@Component({
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.scss'],
})
export class ListBookComponent {
  public title!: string;
  //books: any;
  tableData: any; // Variable para controlar qué conjunto de datos usar en la tabla
  formularioLibro: FormGroup;
  autores: any;
  botonNuevoLibroVisible: boolean = false;
  books!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;

  constructor(
    public fb: FormBuilder,
    public booksService: BooksService,
    public authorsService: AuthorsService
  ) {
    this.formularioLibro = new FormGroup({
      titulo: new FormControl('', Validators.required),
      nombreAutor: new FormControl('', Validators.required),
      apellidosAutor: new FormControl('', Validators.required),
      genero: new FormControl('', Validators.required),
      paginas: new FormControl('', [Validators.required, Validators.min(1)]),
      editorial: new FormControl('', Validators.required),
      descripcion: new FormControl('', Validators.required),
      precio: new FormControl('', [Validators.required, Validators.min(0)])
    });
    
  }

  ngOnInit(): void {
    this.title = 'Lista de libros';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaLibros(0, 10);
    
  }

  cargarTablaLibros(page: number, size: number) {
    // Llamar al servicio para obtener la lista de libros
    this.booksService.getAllBooks(page, size).subscribe(data => {
      this.books = Array.isArray(data.content) ? data.content : [];
      this.totalPaginas = Array.from({length: data.totalPages}, (_, i) => i + 1);
      this.currentPage = data.number;
    });
  }

  recargarTablaLibros() {
    // Llamar al método de cargar tabla de autores para volver a cargar los datos
    this.cargarTablaLibros(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  buscarLibros(keyword: string) {
    // Llamar al servicio para buscar libros por palabra clave
    this.booksService.searchBooksByKeyword(keyword).subscribe(data => {
        this.books = Array.isArray(data.content) ? data.content : [];
        //Cuando busco por keyWord, la respuesta viene en un objeto con la propiedad content que es un array de libros
        this.tableData = this.books; // Cambiar al conjunto de datos de la búsqueda

    });
  }
  guardarLibro() {
    if (this.formularioLibro.valid) {
      this.booksService.addBook(this.formularioLibro.value).subscribe({
        next: (resp) => {
          console.log('Libro guardado:', resp);
          this.botonNuevoLibroVisible = false;
          this.recargarTablaLibros();
        },
        error: (error: HttpErrorResponse) => {
          console.error('Error al guardar el libro:', error);
          // Agregar manejo de errores de interfaz de usuario aquí
        }
      });
    } else {
      console.error('El formulario no está completo');
      // Mostrar un mensaje de error en la interfaz de usuario aquí
      Object.keys(this.formularioLibro.controls).forEach(field => { // {1}
        const control = this.formularioLibro.get(field);            // {2}
        //control.markAsTouched({ onlySelf: true });                  // {3}
      });
    }
  }
  
  


  botonNuevoLibro() {
    this.formularioLibro.reset();
    this.botonNuevoLibroVisible = !this.botonNuevoLibroVisible;
  }
  obtenerLibroPorId(bookId: number) {
    this.booksService.getBookById(bookId).subscribe(
      (response) => {
        this.books = response;
      },
      (error) => {
        console.error('Error al obtener el libro:', error);
        //this.alertaConflicto = true;
        //this.showWarningAlert('Conflicto al obtener el autor.');
      }
    );
  }

  eliminarLibro(books: any) {
    //this.authorsService.deleteAuthorById(book.id).subscribe(
    /*
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
      );*/
  }

  editarLibro(book: any) {
    console.log(book);
  }

  changePage(pageNumber: number): void {
    this.cargarTablaLibros(pageNumber, this.pageSize);
  }

  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement; // Asignación de tipo
    const size = Number(element.value); // Conversión de string a número
    this.pageSize = size;
    this.cargarTablaLibros(0, size);
  }



}
