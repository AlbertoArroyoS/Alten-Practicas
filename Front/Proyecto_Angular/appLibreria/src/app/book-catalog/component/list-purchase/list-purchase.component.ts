import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { LoginService } from 'src/app/services/auth/login.service';
import { BookPurchaseService } from 'src/app/services/book-purchase/book-purchase.service';
import { UserService } from 'src/app/services/users/user.service';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Component({
  selector: 'app-list-purchase',
  templateUrl: './list-purchase.component.html',
  styleUrls: ['./list-purchase.component.scss']
})
export class ListPurchaseComponent implements OnInit, OnDestroy {

  protected subscription: Subscription = new Subscription(); // Gestión de suscripciones
  librosCompra!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  public title: string = 'Libros Comprados';
  userLoginOn$: Observable<boolean>;
  user$: Observable<AuthResponse | null>;
  errorMessage?: string;
  userIdLibreria?: number; // Añadir la propiedad para el idLibreria del usuario
  idUsuario!: number;
  idCliente!: number;
  idLibreria!: number;

  constructor(
    public librosCompradosService: BookPurchaseService,
    private loginService: LoginService,
    private userService: UserService
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
  }
  

  ngOnDestroy(): void {
    // Desuscribirse de todas las suscripciones
    this.subscription.unsubscribe();
  }

  /**
 * Carga los datos del usuario desde el servicio de usuario.
 * @param userId El ID del usuario.
 */
loadUserData(userId: number): void {
  this.userService.getUser(userId).subscribe({
    next: (userData: UserRequest) => { // Asegúrate de que el tipo es UserRequest
      this.idUsuario = userData.idUsuario;
      this.idCliente = userData.idCliente;
      this.idLibreria = userData.idLibreria;
      //console.log('ID Usuario:', this.idUsuario);
      //console.log('ID Cliente:', this.idCliente);
      //console.log('ID Libreria:', this.idLibreria);

      // Cargar las compras del cliente después de obtener los datos del usuario
      //this.loadClientPurchases(0, 10);
      this.cargarTablaLibros(0, 10);
    },
    error: (errorData) => {
      this.errorMessage = errorData;
    },
    complete: () => {
      //console.info("User Data ok");
    }
  });
}


  // Método para recargar la tabla de libros comprados
  recargarTablaLibros(): void {
    this.loadClientPurchases(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  // Método para cambiar la página de la tabla
  changePage(pageNumber: number): void {
    this.loadClientPurchases(pageNumber, this.pageSize);
  }

  // Método para cambiar el tamaño de la página de la tabla
  changePageSize(event: Event): void {
    const element = event.target as HTMLSelectElement; // Asignación de tipo
    const size = Number(element.value); // Conversión de string a número
    this.pageSize = size;
    this.loadClientPurchases(0, size);
  }

  // Método para cargar las compras del cliente
  private loadClientPurchases(page: number, size: number): void {
    //console.log('ID Cliente load purchase:', this.idCliente);
    this.subscription.add(
      this.librosCompradosService.getClientPurchases(this.idCliente, this.currentPage, this.pageSize).subscribe({
        next: (data) => {
          this.librosCompra = Array.isArray(data.content) ? data.content : [];
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

  cargarTablaLibros(page: number, size: number): void {
    this.subscription.add(
      this.librosCompradosService.getClientPurchases(this.idCliente,page, size).subscribe((data) => {
        this.librosCompra = Array.isArray(data.content) ? data.content : [];
        this.totalPaginas = Array.from(
          { length: data.totalPages },
          (_, i) => i + 1
        );
        this.currentPage = data.number;
        //console.log('DataNumber:', data.number);
      })
    );
  }
  
}


/*
    //cargar todos los libros de todos los usuarios
    cargarTablaLibros(page: number, size: number) {
      this.subscription.push(
        // Llamar al servicio para obtener la lista de libros
        this.librosCompradosService.getAllBooksPurchases(page, size).subscribe((data) => {
          this.librosCompra = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        })
      );
    }*/




