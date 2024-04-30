import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
  ) { 
    this.formularioLibro = this.fb.group({
      nombre: new FormControl('', [Validators.required]),
      apellidos: new FormControl('', [Validators.required]),
    });
  }

  ngOnInit(): void {
    /*
    this.formularioAutor = this.fb.group({
      nombre: ['', Validators.required],
      apellidos: ['', Validators.required],
    });
    */

    this.booksService.getAllAuthors().subscribe(resp => {    
      this.autores= resp;
      console.log(resp);
    }, error => {
      console.error(error);
    });
  }

  addAuthor() {
    console.log(this.formularioLibro.value);
    alert('Autor añadido');
  }

}
