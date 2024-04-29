import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms'; // Añade esta línea

import { BookCatalogRoutingModule } from './book-catalog-routing.module';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookComponent } from './pages/book/book.component';
import { AuthorComponent } from './pages/author/author.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';
import { AddAuthorComponent } from './component/add-author/add-author.component';

@NgModule({
  declarations: [
    BookCatalogLayoutComponent,
    BookComponent,
    AuthorComponent,
    BookshopComponent,
    AddAuthorComponent,
  ],
  imports: [
    CommonModule,
    BookCatalogRoutingModule,
    ReactiveFormsModule, // Añade esta línea
  ],
  exports: [
    AuthorComponent
  ]
})
export class BookCatalogModule { }