import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { BooksService } from 'src/app/services/books/books.service';

@Component({
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.scss']
})
export class ListBookComponent {

  public title!: string;
  books: any;

  constructor(
    public booksService: BooksService
  ) { }

  ngOnInit(): void {
    this.title = 'Lista de libros';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaLibros();
  }

  cargarTablaLibros() {
    // Llamar al servicio para obtener la lista de autores
    this.booksService.getAllBooks().subscribe(
      (response) => {
        this.books = response; // Asignar los autores obtenidos a la variable del componente
      },
      (error) => {
        console.error('Error al cargar la tabla de libros:', error);
      }
    );
  }

  recargarTablaLibros() {
    // Llamar al método de cargar tabla de autores para volver a cargar los datos
    this.cargarTablaLibros();
  }

  buscarLibros(keyword: string) {
    // Llamar al servicio para buscar autores por palabra clave
    this.booksService.searchBooksByKeyword(keyword).subscribe(
      (response) => {
        this.books = response; // Actualizar la lista de autores con los resultados de la búsqueda
      },
      (error) => {
        console.error('Error al buscar autores:', error);
      }
    );
  }





}
