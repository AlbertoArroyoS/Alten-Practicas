import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';
import { AuthorComponent } from './pages/author/author.component';
import { BookComponent } from './pages/book/book.component';
import { AddAuthorComponent } from './component/add-author/add-author.component';
import { AddBookComponent } from './component/add-book/add-book.component';
import { ListAuthorComponent } from './component/list-author/list-author.component';
import { ListBookComponent } from './component/list-book/list-book.component';
import { ListPurchaseComponent } from './component/list-purchase/list-purchase.component';
import { ListSoldComponent } from './component/list-sold/list-sold.component';

const routes: Routes = [
  {
    path: '',
    component: BookCatalogLayoutComponent,
    children: [
      { path: 'book', component: BookComponent, title: 'Libro' },
      { path: 'author', component: AuthorComponent, title: 'Autor' },
      { path: 'book-shop', component: BookshopComponent, title: 'Librería' },
      { path: 'author/add-author', component: AddAuthorComponent, title: 'Agregar Autor' },
      { path: 'book/add-book', component: AddBookComponent, title: 'Agregar libro' },
      { path: 'author/list-author', component: ListAuthorComponent, title: 'Lista de autores' },
      { path: 'book/list-book', component: ListBookComponent, title: 'Lista de libros' },
      { path: 'purchases', component: ListPurchaseComponent, title: 'Lista de compras' },
      { path: 'list-book/author/:authorId', component: ListBookComponent , title: 'Libros del autor' }, // Ruta con parámetro
      { path: 'solds', component: ListSoldComponent, title: 'Lista de ventas' },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookCatalogRoutingModule { }
