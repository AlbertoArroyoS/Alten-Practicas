import { Component, inject} from '@angular/core';
import { Title } from '@angular/platform-browser';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent {
  //inyectamos dependencias

  private router: Router = inject(Router);

  constructor() { 

  }

  public goToBook(): void {
    this.router.navigate(['book-catalog/book']);
    
  }

  public goToBookList(): void {
    this.router.navigate(['book-catalog/book/list-book']);
  }

  public goAddBook(): void {
    this.router.navigate(['book-catalog/book/add-book']);
  }

  
}
