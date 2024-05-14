import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  //inyectamos dependencias

  private router: Router = inject(Router);

  constructor() { 

  }

  public goToBook(): void {
    this.router.navigate(['book-catalog/book']);
    
  }

  public goToBookShop(): void {
    this.router.navigate(['book-catalog/book-shop']);
  }

  public goToAuthor(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToAdminBook(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToAdminBookShop(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToAdminAuthor(): void {
    this.router.navigate(['book-catalog/author']);
  }

  public goToPurchases(): void {
    this.router.navigate(['book-catalog/purchases']);
  }



}
