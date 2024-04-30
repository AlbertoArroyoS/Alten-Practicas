import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


import { BookCatalogRoutingModule } from './book-catalog-routing.module';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookComponent } from './pages/book/book.component';
import { AuthorComponent } from './pages/author/author.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';
import { AddAuthorComponent } from './component/add-author/add-author.component';
import { AddBookComponent } from './component/add-book/add-book.component';

@NgModule({
  declarations: [
    BookCatalogLayoutComponent,
    BookComponent,
    AuthorComponent,
    BookshopComponent,
    AddAuthorComponent,
    AddBookComponent,
  ],
  imports: [
    CommonModule,
    BookCatalogRoutingModule,
    ReactiveFormsModule, 
    FormsModule,
  ],
  exports: [
    AuthorComponent
  ]
})
export class BookCatalogModule { }