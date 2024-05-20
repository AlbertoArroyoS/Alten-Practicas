import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BookCatalogRoutingModule } from './book-catalog-routing.module';

import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookComponent } from './pages/book/book.component';
import { AuthorComponent } from './pages/author/author.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';
import { AddAuthorComponent } from './component/add-author/add-author.component';
import { AddBookComponent } from './component/add-book/add-book.component';
import { ListAuthorComponent } from './component/list-author/list-author.component';
import { ListBookComponent } from './component/list-book/list-book.component';
import { ListBookShopComponent } from './component/list-book-shop/list-book-shop.component';
import { PurchaseComponent } from './pages/purchase/purchase.component';
import { ListPurchaseComponent } from './component/list-purchase/list-purchase.component';

@NgModule({
  declarations: [
    BookCatalogLayoutComponent,
    BookComponent,
    AuthorComponent,
    BookshopComponent,
    AddAuthorComponent,
    AddBookComponent,
    ListAuthorComponent,
    ListBookComponent,
    ListBookShopComponent,
    PurchaseComponent,
    ListPurchaseComponent,
  ],
  imports: [
    CommonModule,
    BookCatalogRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ]
})
export class BookCatalogModule { }
