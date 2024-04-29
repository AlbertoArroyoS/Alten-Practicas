import { Title } from '@angular/platform-browser';
import { Component , OnInit} from '@angular/core';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent {
  public title!: string;


  ngOnInit(): void {

    this.title = 'Lista de autores';


  }

}

