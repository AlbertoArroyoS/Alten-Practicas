import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { LoginService } from 'src/app/services/auth/login.service';
import { AuthorsService } from 'src/app/services/authors/authors.service';
import { BookShopService } from 'src/app/services/book-shop/book-shop.service';
import { BooksService } from 'src/app/services/books/books.service';
import { UserService } from 'src/app/services/users/user.service';
import { BookToShopLibrary } from 'src/app/shared/model/request/bookToShopLibrary';
import { UserRequest } from 'src/app/shared/model/request/userRequest';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';

// Decorador del componente
@Component({
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.scss'],
})
// Clase del componente
export class ListBookComponent implements OnInit, OnDestroy {
  // Declaración de variables
  protected subscription: Subscription = new Subscription(); // Gestión de suscripciones
  public title: string = 'Lista de libros';
  book: any;
  tableData: any; // Variable para controlar qué conjunto de datos usar en la tabla
  librosCompra!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  autores: any;
  botonNuevoLibroVisible: boolean = false;
  books!: any[];
  modificarLibro: boolean = false;
  mostrarBotonGuardar: boolean = true;
  alertaConflicto: boolean = false;
  successMessage: string = 'Libro guardado correctamente';
  warningMessage: string = '';
  eliminadoExitoso: boolean = false;
  guardadoExitoso: boolean = false;
  userLoginOn$: Observable<boolean>;
  user$: Observable<AuthResponse | null>;
  userData?: UserRequest; // Nota: Cambiado a UserRequest
  errorMessage?: string;
  idUsuario!: number;
  idCliente!: number;
  idLibreria!: number;

  // Referencias a elementos del DOM
  @ViewChild('content', { static: true }) modalContent!: ElementRef;
  @ViewChild('addBookModal', { static: true }) addBookModal!: ElementRef;

  // Formulario principal y para agregar libros a la librería
  formularioLibro: FormGroup;
  public addBookForm: FormGroup;
  private currentBookId!: number;

  // Constructor de la clase
  constructor(
    public fb: FormBuilder,
    public booksService: BooksService,
    public authorsService: AuthorsService,
    private modalService: NgbModal,
    private bookShopService: BookShopService,
    private loginService: LoginService, // Inyectar AuthService
    private userService: UserService,
  ) {
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;

    // Inicialización del formulario principal
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

    // Inicialización del formulario para agregar libros a la librería
    this.addBookForm = this.fb.group({
      cantidad: ['', [Validators.required, Validators.min(1)]],
      precio: ['', [Validators.required, Validators.min(0.01)]],
      edicion: ['', [Validators.required, Validators.min(1)]],
      fechaPublicacion: ['', [Validators.required, Validators.pattern(/^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\d{4}$/)]]
    });
  }

  // Método que se ejecuta al inicializar el componente
  ngOnInit(): void {
    // Cargar libros al iniciar
    this.loadBooks();
    
    // Suscribirse a los datos del usuario
    this.subscription.add(
      this.user$.subscribe({
        next: (userData) => {
          if (userData) {
            this.loadUserData(userData.idUsuario);
          }
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar datos del usuario.';
          console.error(error);
        }
      })
    );
    // Cargar datos del usuario si ya está logueado
    const userId = this.loginService.getUserId();
    if (userId) {
      this.loadUserData(userId);
    }
  }

  // Método que se ejecuta al destruir el componente y desuscribirse de las suscripciones
  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

   /**
   * Carga los datos del usuario desde el servicio de usuario.
   * @param userId El ID del usuario.
   */
   loadUserData(userId: number): void {
    this.userService.getUser(userId).subscribe({
      next: (userData: UserRequest) => { // Asegúrate de que el tipo es UserRequest
        this.userData = userData;
        this.idUsuario = this.userData.idUsuario;
        this.idCliente = this.userData.idCliente;
        this.idLibreria = this.userData.idLibreria;
        // Aquí puedes utilizar los datos del usuario para otras operaciones, si es necesario
      },
      error: (errorData) => {
        this.errorMessage = errorData;
      },
      complete: () => {
        console.info("User Data ok");
      }
    });
  }

  // Método para cargar la lista de libros
  private loadBooks(): void {
    this.subscription.add(
      this.booksService.getAllBooks(this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.books = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar la lista de libros.';
          console.error('Error fetching books', error);
        }
      })
    );
  }

  // Método para buscar libros
  buscarLibros(keyword: string) {
    this.subscription.add(
      this.booksService.searchBooksByKeyword(keyword).subscribe({
        next: (data) => {
          this.books = Array.isArray(data.content) ? data.content : [];
          //Cuando busco por keyWord, la respuesta viene en un objeto con la propiedad content que es un array de libros
          this.tableData = this.books; // Cambiar al conjunto de datos de la búsqueda
        },
        error: (error) => {
          this.errorMessage = 'Error al buscar libros.';
          console.error('Error searching books', error);
        }
      })
    );
  }

  // Método para recargar la tabla de libros
  recargarTablaLibros(): void {
    this.loadBooks();
    this.paginacion = true;
  }

  // Método para cambiar la página de la tabla
  changePage(pageNumber: number): void {
    this.currentPage = pageNumber;
    this.loadBooks();
  }

  // Método para cambiar el tamaño de la página de la tabla
  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement;
    const size = Number(element.value);
    this.pageSize = size;
    this.changePage(0);
  }

  // Método para guardar un libro
  guardarLibro(): void {
    if (this.formularioLibro.valid) {
      this.subscription.add(
        this.booksService.addBook(this.formularioLibro.value).subscribe({
          next: (resp) => {
            this.showSuccessAlert('Libro guardado correctamente');
            this.botonNuevoLibroVisible = false;
            this.recargarTablaLibros();
          },
          error: (error: HttpErrorResponse) => {
            this.showWarningAlert('Conflicto al guardar el libro.');
          },
        })
      );
    } else {
      this.formularioLibro.markAllAsTouched();
      this.showWarningAlert('El formulario no está completo');
    }
  }

  // Método para mostrar el formulario de nuevo libro
  botonNuevoLibro(): void {
    this.formularioLibro.reset();
    this.botonNuevoLibroVisible = !this.botonNuevoLibroVisible;
  }

  // Método para obtener un libro por su ID
  obtenerLibroPorId(bookId: number): void {
    this.subscription.add(
      this.booksService.getBookById(bookId).subscribe({
        next: (response) => {
          this.book = response;
          this.abrirModal(this.book);
        },
        error: (error) => {
          this.showWarningAlert('Conflicto al obtener el libro.');
        }
      })
    );
  }

  // Método para abrir el modal
  abrirModal(book: any): void {
    this.book = book;
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
  editarLibro(book: any): void {
    window.scrollTo({ top: 0, behavior: 'smooth' });
    this.botonNuevoLibroVisible = true;
    this.modificarLibro = true;
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

  // Método para actualizar un libro
  actualizarLibro(): void {
    const idControl = this.formularioLibro.get('id');
    if (idControl) {
      const libroId = idControl.value;
      this.subscription.add(
        this.booksService.updateBook(libroId, this.formularioLibro.value).subscribe({
          next: (resp) => {
            window.scrollTo({ top: 0, behavior: 'smooth' });
            this.guardadoExitoso = true;
            this.botonNuevoLibroVisible = false;
            this.modificarLibro = false;
            this.formularioLibro.reset();
            const index = this.books.findIndex((a) => a.id === libroId);
            if (index !== -1) this.books[index] = resp;
            this.showSuccessAlert('Libro actualizado correctamente');
            this.recargarTablaLibros();
          },
          error: (error: HttpErrorResponse) => {
            this.showWarningAlert('Conflicto al actualizar el libro. El libro ya existe.');
          }
        })
      );
    }
  }

  // Método para eliminar un libro
  eliminarLibro(book: any): void {
    this.subscription.add(
      this.booksService.deleteBookById(book.id).subscribe({
        next: (resp) => {
          window.scrollTo({ top: 0, behavior: 'smooth' });
          this.recargarTablaLibros();
          this.showSuccessAlert('Libro eliminado correctamente');
        },
        error: (error) => {
          this.showWarningAlert('Conflicto al eliminar el libro. El libro está asociado.');
        }
      })
    );
  }

  // Método para abrir el modal de agregar libro a la librería
  openAddBookModal(bookId: number): void {
    this.currentBookId = bookId;
    this.modalService.open(this.addBookModal);
  }

  // Método para confirmar la adición del libro a la librería
 // Método para confirmar la adición del libro a la librería
confirmAddBook(modal: NgbModalRef): void {
  if (this.addBookForm.valid && this.userData) {
    const book: BookToShopLibrary = {
      idLibro: this.currentBookId,
      idLibreria: this.userData.idLibreria,
      cantidad: this.addBookForm.value.cantidad,
      precio: this.addBookForm.value.precio,
      edicion: this.addBookForm.value.edicion,
      fechaPublicacion: this.addBookForm.value.fechaPublicacion
    };

    this.subscription.add(
      this.bookShopService.addBookToLibrary(book).subscribe({
        next: (response) => {
          this.book = response;
          modal.close(); // Cierra el modal actual
          this.showSuccessAlert('Libro agregado a la librería correctamente.');
          // Remueve la llamada a abrir el segundo modal.
        },
        error: (error) => {
          this.showWarningAlert('Conflicto al agregar el libro a la librería.');
        }
      })
    );
  } else {
    this.showWarningAlert('Por favor, completa todos los campos correctamente.');
  }
}

  // Método para mostrar una alerta de éxito
  showSuccessAlert(message: string): void {
    this.guardadoExitoso = true;
    this.successMessage = message;
    setTimeout(() => {
      this.guardadoExitoso = false;
    }, 4000);
  }

  // Método para mostrar una alerta de advertencia
  showWarningAlert(message: string): void {
    this.warningMessage = message;
    this.alertaConflicto = true;
    setTimeout(() => {
      this.alertaConflicto = false;
    }, 4000);
  }
}
