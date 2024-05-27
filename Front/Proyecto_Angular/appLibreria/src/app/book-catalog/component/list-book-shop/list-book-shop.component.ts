// list-book-shop.component.ts
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, OnDestroy, ViewChild, TemplateRef } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { LoginService } from 'src/app/services/auth/login.service';
import { BookShopService } from 'src/app/services/book-shop/book-shop.service';
import { BookPurchaseService } from 'src/app/services/book-purchase/book-purchase.service';
import { UserService } from 'src/app/services/users/user.service';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Component({
  selector: 'app-list-book-shop',
  templateUrl: './list-book-shop.component.html',
  styleUrls: ['./list-book-shop.component.scss']
})
export class ListBookShopComponent implements OnInit, OnDestroy {
  protected subscription: Subscription = new Subscription(); // Gestión de suscripciones
  librosVenta!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  public title: string = 'Libros a la venta';
  userLoginOn$: Observable<boolean>;
  user$: Observable<AuthResponse | null>;
  userData?: UserRequest; // Nota: Cambiado a UserRequest
  errorMessage?: string;
  idUsuario!: number;
  idCliente!: number;
  idLibreria!: number;
  sortDirection = true; // true = ascendente, false = descendente

  @ViewChild('confirmationModal') confirmationModal!: TemplateRef<any>;

  constructor(
    public ventaLibroService: BookShopService,
    private loginService: LoginService,
    private userService: UserService,
    private bookPurchaseService: BookPurchaseService, // Inyecta el servicio de compra de libros
    private modalService: NgbModal // Inyecta el servicio de modal
  ) {
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;
  }

  ngOnInit(): void {
    // Suscribirse a los datos del usuario
    this.subscription.add(
      this.user$.subscribe({
        next: (userData) => {
          if (userData) {
            this.loadUserData(userData.idUsuario);
            //console.log('User Data:', userData);
          }
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar datos del usuario.';
          console.error(error);
        }
      })
    );

    // Realizar una carga inicial de la tabla de libros al inicializar el componente
    this.cargarTablaLibros(0, 10);
  }

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
        //console.log('ID Usuario:', this.idUsuario);
        //console.log('ID Cliente:', this.idCliente);
        //console.log('ID Libreria:', this.idLibreria);
      },
      error: (errorData) => {
        this.errorMessage = errorData;
      }
    });
  }

  // Método para cargar la tabla de libros
  cargarTablaLibros(page: number, size: number): void {
    this.subscription.add(
      this.ventaLibroService.getAllBooksShell(page, size).subscribe((data) => {
        this.librosVenta = Array.isArray(data.content) ? data.content : [];
        this.totalPaginas = Array.from(
          { length: data.totalPages },
          (_, i) => i + 1
        );
        this.currentPage = data.number;
        //console.log('DataNumber:', data.number);
      })
    );
  }

  // Nueva función para cargar libros por idCliente
  cargarLibrosPorLibreria(): void {
    this.subscription.add(
      this.ventaLibroService.getBooksByIdLibreria(this.idLibreria, this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          console.log('*IdLibreria:', this.idLibreria);
          //console.log('****Data:', data); // Para verificar los datos recibidos
          this.librosVenta = Array.isArray(data.content) ? data.content : [];
          //console.log('****Books:', this.librosVenta); // Para verificar que los libros se están asignando
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
          this.paginacion = data.totalPages > 1;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar la lista de libros.';
          console.error('Error fetching books', error);
        }
      })
    );
  }

   // Nueva función para cargar libros excluyendo idLibreria
   cargarLibrosMenosLibreria(): void {
    this.subscription.add(
      this.ventaLibroService.getBooksExcludingLibreria(this.idLibreria, this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.librosVenta = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
          this.paginacion = data.totalPages > 1;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar la lista de libros.';
          console.error('Error fetching books', error);
        }
      })
    );
  }

  // Método para recargar la tabla de libros
  recargarTablaLibros(): void {
    this.cargarTablaLibros(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  // Método para formatear la fecha actual en formato dd-MM-yyyy
  private obtenerFechaActual(): string {
    const date = new Date();
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();
    return `${day}-${month}-${year}`;
  }

  // Método para comprar un libro
  comprarLibro(idLibro: number, precio: number, idLibreria: number, idLibreriaLibro: number): void {
    if (this.idCliente) {
      const fechaCompra = this.obtenerFechaActual(); // Obtiene la fecha actual formateada
      const purchaseData = {
        fechaCompra: fechaCompra,
        precio: precio,
        idCliente: this.idCliente,
        idLibro: idLibro,
        idLibreria: idLibreria,
        idLibreriaLibro: idLibreriaLibro
      };

      this.subscription.add(
        this.bookPurchaseService.purchaseBook(purchaseData).subscribe({
          next: (response) => {
            console.log('Compra realizada:', response);
            this.modalService.open(this.confirmationModal); // Abre el modal de confirmación
            this.recargarTablaLibros();
          },
          error: (error: HttpErrorResponse) => {
            this.errorMessage = 'Error al realizar la compra.';
            console.error('Error purchasing book', error);
          }
        })
      );
    } else {
      this.errorMessage = 'No se pudo obtener el ID del cliente.';
    }
  }

  // Método para cambiar la página de la tabla
  changePage(pageNumber: number): void {
    this.cargarTablaLibros(pageNumber, this.pageSize);
  }

  // Método para cambiar el tamaño de la página de la tabla
  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement;
    const size = Number(element.value);
    this.pageSize = size;
    this.cargarTablaLibros(0, size);
  }

  // Método para ordenar la tabla
  sortTable(column: string): void {
    this.sortDirection = !this.sortDirection;
    const direction = this.sortDirection ? 1 : -1;

    this.librosVenta.sort((a, b) => {
      if (a[column] < b[column]) {
        return -1 * direction;
      } else if (a[column] > b[column]) {
        return 1 * direction;
      } else {
        return 0;
      }
    });
  }

  // Método para buscar libros por título
  buscarLibrosPorTitulo(titulo: string): void {
    this.subscription.add(
      this.ventaLibroService.searchBooksByTitle(titulo, this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.librosVenta = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        },
        error: (error: HttpErrorResponse) => {
          this.errorMessage = 'Error al buscar los libros.';
          console.error('Error searching books', error);
        }
      })
    );
  }
}
