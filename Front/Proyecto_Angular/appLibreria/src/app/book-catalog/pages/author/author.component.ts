import { Title } from '@angular/platform-browser';
import { Component , OnInit, inject} from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent {
  //inyectamos dependencias

  private router: Router = inject(Router);

  constructor() { 

  }
  public goToAuthor(): void {
    this.router.navigate(['book-catalog/author']);
    
  }

  public goToAuthorList(): void {
    this.router.navigate(['book-catalog/author/list-author']);
  }

  public goAddAuthor(): void {
    this.router.navigate(['book-catalog/author/add-author']);
  }



}

