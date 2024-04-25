import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BookCatalogRoutingModule } from './book-catalog-routing.module';
import { BooCatalogLayoutComponent } from './layout/boo-catalog-layout/boo-catalog-layout.component';


@NgModule({
  declarations: [
    BooCatalogLayoutComponent
  ],
  imports: [
    CommonModule,
    BookCatalogRoutingModule
  ]
})
export class BookCatalogModule { }
