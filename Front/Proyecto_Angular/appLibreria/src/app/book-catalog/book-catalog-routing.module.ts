import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';
import { AuthorComponent } from './pages/author/author.component';
import { BookComponent } from './pages/book/book.component';
import { AddAuthorComponent } from './component/add-author/add-author.component';
import { AddBookComponent } from './component/add-book/add-book.component';

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
      { path: 'add-author', component: AddAuthorComponent, title: 'Agregar Autor'},
      { path: 'add-book', component: AddBookComponent, title: 'Agregar libro'}
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class BookCatalogRoutingModule {}
