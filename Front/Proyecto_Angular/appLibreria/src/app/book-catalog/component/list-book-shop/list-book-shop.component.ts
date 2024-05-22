import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { LoginService } from 'src/app/services/auth/login.service';
import { BookShopService } from 'src/app/services/book-shop/book-shop.service';
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

  constructor(
    public ventaLibroService: BookShopService,
    private loginService: LoginService,
    private userService: UserService,
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
        console.log('ID Usuario:', this.idUsuario);
        this.idCliente = this.userData.idCliente;
        console.log('ID Cliente:', this.idCliente);
        this.idLibreria = this.userData.idLibreria;
        console.log('ID Libreria:', this.idLibreria);
      },
      error: (errorData) => {
        this.errorMessage = errorData;
      },
      complete: () => {
        console.info("User Data ok");
      }
    });
  }

  // Método para cargar la tabla de libros
  cargarTablaLibros(page: number, size: number): void {
    this.subscription.add(
      // Llamar al servicio para obtener la lista de libros
      this.ventaLibroService.getAllBooksShell(page, size).subscribe((data) => {
        this.librosVenta = Array.isArray(data.content) ? data.content : [];
        this.totalPaginas = Array.from(
          { length: data.totalPages },
          (_, i) => i + 1
        );
        this.currentPage = data.number;
        console.log('Libros Venta:', this.librosVenta);
      })
    );
  }

  // Método para recargar la tabla de libros
  recargarTablaLibros(): void {
    // Llamar al método de cargar tabla de libros para volver a cargar los datos
    this.cargarTablaLibros(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  // Método para comprar un libro (pendiente de implementación)
  comprarLibro(id: number): void {
    // Lógica para comprar el libro
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
}
