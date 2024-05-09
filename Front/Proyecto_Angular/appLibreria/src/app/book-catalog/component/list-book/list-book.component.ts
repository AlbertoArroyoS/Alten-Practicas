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
//import { NgbModule } from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.scss'],
})
export class ListBookComponent {
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
  @ViewChild('content', { static: true }) modalContent!: ElementRef;
  modificarLibro: boolean = false;
  mostrarBotonGuardar: boolean = true;

  constructor(
    public fb: FormBuilder,
    public booksService: BooksService,
    public authorsService: AuthorsService,
    private modalService: NgbModal
  ) {
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
      this.book = response; // Asigna el libro obtenido a una variable local
      this.abrirModal(this.book); // Llama al método openModal con el libro
    },
    (error) => {
      console.error('Error al obtener el libro:', error);
      // Manejar errores aquí
    }
  );
  }

  abrirModal(book: any) {
    this.book = book; // Asigna el libro a la variable de la clase para que esté disponible en el template
    this.modalService.open(this.modalContent, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      // Manejar el resultado si es necesario
    }, (reason) => {
      // Manejar el cierre del modal si es necesario
    });
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

  actualizarAutor() {
    const idControl = this.formularioLibro.get('id');
    if (idControl) {
      const libroId = idControl.value;
      this.booksService.updateBook(libroId, this.formularioLibro.value).subscribe(
        (resp) => {
          this.botonNuevoLibroVisible = false;
          this.formularioLibro.reset();
          const index = this.books.findIndex(a => a.id === libroId);
          if (index !== -1) this.books[index] = resp; // Actualiza solo el autor modificado
          //this.showSuccessAlert('Autor actualizado correctamente');
          setTimeout(() => {
           // this.guardadoExitoso = false;
          }, 3000);
          this.recargarTablaLibros();
        },
        (error: HttpErrorResponse) => {
         // this.alertaConflicto = true;
         // this.showWarningAlert('Conflicto al guardar el autor. El autor ya existe.');
        }
      );      
    }
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
