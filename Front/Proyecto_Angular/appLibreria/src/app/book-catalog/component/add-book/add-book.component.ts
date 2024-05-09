import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthorsService } from 'src/app/services/authors/authors.service';
import {BooksService} from 'src/app/services/books/books.service';

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.scss']
})
export class AddBookComponent {

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
    /*
    this.formularioAutor = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
    });
    */

    //Metodo para obtener los autores que hay en la base de datos y poder seleccionarlos en el formulario
    /*
    this.authorsService.getAllAuthors().subscribe(resp => {    
      this.autores= resp;
      //console.log(resp);
    }, error => {
      console.error(error);
    });*/
  }

  addBook() {
    console.log(this.formularioLibro.value);
    alert('Autor añadido');
  }

}
