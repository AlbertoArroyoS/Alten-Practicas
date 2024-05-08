import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthorsService } from 'src/app/services/authors/authors.service';
import { BooksService } from 'src/app/services/books/books.service';

@Component({
  selector: 'app-list-book',
  templateUrl: './list-book.component.html',
  styleUrls: ['./list-book.component.scss']
})
export class ListBookComponent {

  public title!: string;
  books: any;
  tableData: any; // Variable para controlar qué conjunto de datos usar en la tabla
  formularioLibro: FormGroup;
  autores : any;


  constructor(
    public fb: FormBuilder,
    public booksService: BooksService,
    public authorsService: AuthorsService
  ) { 
    this.formularioLibro = this.fb.group({
      titulo: new FormControl('', [Validators.required]),
      autor: ['', Validators.required],
      genero: new FormControl('', [Validators.required]),
      paginas: new FormControl('', [Validators.required]),
      editorial: new FormControl('', [Validators.required]),
      descripcion: new FormControl('', [Validators.required])
    });
  }

  ngOnInit(): void {
    this.title = 'Lista de libros';
    // Realizar una carga inicial de la tabla de autores al inicializar el componente
    this.cargarTablaLibros();
    this.authorsService.getAllAuthors().subscribe(resp => {    
      this.autores= resp;
      //console.log(resp);
    }, error => {
      console.error(error);
    });
  }

  cargarTablaLibros() {
    // Llamar al servicio para obtener la lista de libros
    this.booksService.getAllBooks().subscribe(
      (response) => {
        this.books = response;
        this.tableData = this.books; // Asignar los libros obtenidos al conjunto de datos de la tabla
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
    // Llamar al servicio para buscar libros por palabra clave
    this.booksService.searchBooksByKeyword(keyword).subscribe(
      (response) => {
        this.books = response;
        //Cuando busco por keyWord, la respuesta viene en un objeto con la propiedad content que es un array de libros
        this.tableData = this.books.content; // Cambiar al conjunto de datos de la búsqueda
      },
      (error) => {
        console.error('Error al buscar libros:', error);
      }
    );
  }
  addBook() {
    console.log(this.formularioLibro.value);
    alert('Autor añadido');
  }
}






