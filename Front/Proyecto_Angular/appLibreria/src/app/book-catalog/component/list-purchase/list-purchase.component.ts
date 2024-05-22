import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { LoginService } from 'src/app/services/auth/login.service';
import { BookPurchaseService } from 'src/app/services/book-purchase/book-purchase.service';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';
import { switchMap } from 'rxjs/operators';

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

  constructor(
    public librosCompradosService: BookPurchaseService,
    private loginService: LoginService
  ) {
    this.userLoginOn$ = this.loginService.userLoginOn$;
    this.user$ = this.loginService.user$;
  }

  ngOnInit(): void {
    // Cargar las compras del cliente al inicializar el componente
    this.subscription.add(
      this.user$.pipe(
        switchMap(userData => {
          if (userData) {
            this.userIdLibreria = userData.idLibreria; // Asignar el idLibreria del usuario
            return this.librosCompradosService.getClientPurchases(userData.idUsuario, this.currentPage, this.pageSize);
          } else {
            return [];
          }
        })
      ).subscribe({
        next: (data) => {
          this.librosCompra = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar las compras del cliente.';
          console.error('Error fetching client purchases', error);
        }
      })
    );
  }

  ngOnDestroy(): void {
    // Desuscribirse de todas las suscripciones
    this.subscription.unsubscribe();
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
    this.subscription.add(
      this.user$.pipe(
        switchMap(userData => {
          if (userData) {
            return this.librosCompradosService.getClientPurchases(userData.idUsuario, page, size);
          } else {
            return [];
          }
        })
      ).subscribe({
        next: (data) => {
          this.librosCompra = Array.isArray(data.content) ? data.content : [];
          this.totalPaginas = Array.from(
            { length: data.totalPages },
            (_, i) => i + 1
          );
          this.currentPage = data.number;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar las compras del cliente.';
          console.error('Error fetching client purchases', error);
        }
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




