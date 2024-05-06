import { BooksService } from 'src/app/services/books/books.service';
import { AuthorsService } from './../../../services/authors/authors.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-list-author',
  templateUrl: './list-author.component.html',
  styleUrls: ['./list-author.component.scss'],
})
export class ListAuthorComponent {
  public title!: string;
  autores: any;
  book: any;

  constructor(
    public authorsService: AuthorsService,
    private booksService: BooksService
  ) {}

  ngOnInit(): void {
    this.title = 'Lista de autores';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaAutores();
  }

  cargarTablaAutores() {
    // Llamar al servicio para obtener la lista de autores
    this.authorsService.getAllAuthors().subscribe(
      (response) => {
        this.autores = response; // Asignar los autores obtenidos a la variable del componente
      },
      (error) => {
        console.error('Error al cargar la tabla de autores:', error);
      }
    );
  }

  recargarTablaAutores() {
    // Llamar al método de cargar tabla de autores para volver a cargar los datos
    this.cargarTablaAutores();
  }

  buscarAutores(keyword: string) {
    // Llamar al servicio para buscar autores por palabra clave
    this.authorsService.searchAuthorsByKeyword(keyword).subscribe(
      (response) => {
        this.autores = response; // Actualizar la lista de autores con los resultados de la búsqueda
      },
      (error) => {
        console.error('Error al buscar autores:', error);
      }
    );
  }

  obtenerLibroPorId(bookId: number) {
    this.booksService.getBookById(bookId).subscribe(
      (response) => {
        this.book = response; // Asignar el libro obtenido a la variable del componente
      },
      (error) => {
        console.error('Error al obtener el libro:', error);
      }
    );
  }
}
