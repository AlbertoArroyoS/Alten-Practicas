import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookCatalogRoutingModule } from './book-catalog-routing.module';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';


@NgModule({
  declarations: [
    BookCatalogLayoutComponent
  ],
  imports: [
    CommonModule,
    BookCatalogRoutingModule
  ]
})
export class BookCatalogModule { }
