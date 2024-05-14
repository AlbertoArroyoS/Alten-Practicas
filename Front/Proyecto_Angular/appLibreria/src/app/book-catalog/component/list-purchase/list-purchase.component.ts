import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { BookPurchaseService } from 'src/app/services/book-purchase/book-purchase.service';

@Component({
  selector: 'app-list-purchase',
  templateUrl: './list-purchase.component.html',
  styleUrls: ['./list-purchase.component.scss']
})
export class ListPurchaseComponent {

  protected subscription: Array<Subscription> = new Array(); // Array de suscripciones
  librosCompra!: any[];
  totalPaginas?: number[];
  currentPage: number = 0;
  pageSize: number = 10;
  paginacion: boolean = true;
  public title!: string;
  
  constructor(
    public librosCompradosService: BookPurchaseService,
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
      this.librosCompradosService.getAllBooksPurchases(page, size).subscribe((data) => {
        this.librosCompra = Array.isArray(data.content) ? data.content : [];
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
