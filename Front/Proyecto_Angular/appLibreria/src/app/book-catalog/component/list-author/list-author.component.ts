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

  constructor(public AuthorsService: AuthorsService) {}

  ngOnInit(): void {
    this.title = 'Lista de autores';
    this.AuthorsService.getAllAuthors().subscribe(
      (resp) => {
        this.autores = resp;
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
