import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookCatalogRoutingModule } from './book-catalog-routing.module';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookComponent } from './pages/book/book.component';
import { AutorComponent } from './pages/autor/autor.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';


@NgModule({
  declarations: [
    BookCatalogLayoutComponent,
    BookComponent,
    AutorComponent,
    BookshopComponent,
  ],
  imports: [
    CommonModule,
    BookCatalogRoutingModule
  ]
})
export class BookCatalogModule { }
