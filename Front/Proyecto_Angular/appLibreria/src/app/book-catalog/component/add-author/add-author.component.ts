import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-add-author',
  templateUrl: './add-author.component.html',
  styleUrls: ['./add-author.component.scss']
})
export class AddAuthorComponent implements OnInit{

  formularioAutor: FormGroup;

  constructor(private form: FormBuilder) { 

    this.formularioAutor = this.form.group({
      nombre: new FormControl('',[]),
      apellidos: new FormControl('',[]),
    });

  }

  ngOnInit(): void {
    
  }

  addAuthor(){
    console.log(this.formularioAutor.value);
    alert('Autor añadido');
  }

}
