import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { BookShopService } from 'src/app/services/book-shop/book-shop.service';

@Component({
  selector: 'app-list-book-shop',
  templateUrl: './list-book-shop.component.html',
  styleUrls: ['./list-book-shop.component.scss']
})
export class ListBookShopComponent {
  protected subscription: Array<Subscription> = new Array(); // Array de suscripciones
  librosVenta!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  public title!: string;
  
  constructor(
    public ventaLibroService: BookShopService,
  ){

  }

  // Método que se ejecuta al inicializar el componente
  ngOnInit(): void {
    this.title = 'Libros a la venta';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaLibros(0, 10);
  }

  // Método que se ejecuta al destruir el componente y desuscribirse de las suscripciones
  ngOnDestroy(): void {
    // Desuscribirse de cada suscripción en el array de suscripciones
    this.subscription.forEach((subscription) => {
      subscription.unsubscribe();
    });
  }

  // Método para cargar la tabla de libros
  cargarTablaLibros(page: number, size: number) {
    this.subscription.push(
      // Llamar al servicio para obtener la lista de libros
      this.ventaLibroService.getAllBooksShell(page, size).subscribe((data) => {
        this.librosVenta = Array.isArray(data.content) ? data.content : [];
        this.totalPaginas = Array.from(
          { length: data.totalPages },
          (_, i) => i + 1
        );
        this.currentPage = data.number;
      })
    );
  }

  // Método para recargar la tabla de libros
  recargarTablaLibros() {
    // Llamar al método de cargar tabla de autores para volver a cargar los datos
    this.cargarTablaLibros(this.currentPage, this.pageSize);
    this.paginacion = true;
  }

  
  comprarLibro(id: number) {
    
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
