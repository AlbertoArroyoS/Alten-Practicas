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

  constructor(private form: FormBuilder) { 

    this.formularioLibro = this.form.group({
      nombre: new FormControl('',[Validators.required]),
      apellidos: new FormControl('',[Validators.required]),
    });

  }

  ngOnInit(): void {
    
  }

}
