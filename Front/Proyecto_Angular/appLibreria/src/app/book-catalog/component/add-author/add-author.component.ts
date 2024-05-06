import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { AuthorsService } from 'src/app/services/authors/authors.service';

@Component({
  selector: 'app-add-author',
  templateUrl: './add-author.component.html',
  styleUrls: ['./add-author.component.scss'],
})
export class AddAuthorComponent implements OnInit {
  formularioAutor: FormGroup;
  autores: any;
  guardadoExitoso: boolean = false;

  constructor(public fb: FormBuilder, public authorsService: AuthorsService) {
    this.formularioAutor = this.fb.group({
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
    /*
    this.authorsService.getAllAuthors().subscribe(resp => {    
      this.autores= resp;
    }, error => {
      console.error(error);
    });*/
  }

  addAuthor() {
    this.authorsService.addAuthor(this.formularioAutor.value).subscribe(
      (resp) => {
        // Resetear el formulario una vez que se ha añadido el autor
        this.formularioAutor.reset();
        this.autores = resp;
  
        // Activar la variable guardadoExitoso para mostrar la alerta de Bootstrap
        this.guardadoExitoso = true;
  
        // Desactivar la variable después de unos segundos para ocultar la alerta
        setTimeout(() => {
          this.guardadoExitoso = false;
        }, 3000); // Ocultar la alerta después de 3 segundos
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
