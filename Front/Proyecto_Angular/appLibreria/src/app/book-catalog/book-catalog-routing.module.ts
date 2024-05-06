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

const routes: Routes = [
  //el children lo utilizo para derfinir las rutas secundarias
  {
    path: '',
    component: BookCatalogLayoutComponent,
    children: [
      { path: 'book', component: BookComponent , title: 'Libro'},
      //{path: '', redirectTo: 'book', pathMatch: 'full'},
      { path: 'author', component: AuthorComponent, title: 'Autor'},
      { path: 'book-shop', component: BookshopComponent, title: 'Librería'},
      { path: 'author/add-author', component: AddAuthorComponent, title: 'Agregar Autor'},
      { path: 'book/add-book', component: AddBookComponent, title: 'Agregar libro'},
      { path : 'author/list-author', component: ListAuthorComponent, title: 'Lista de autores'},
      { path : 'book/list-book', component: ListBookComponent, title: 'Lista de libros'}
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BookCatalogRoutingModule {}
