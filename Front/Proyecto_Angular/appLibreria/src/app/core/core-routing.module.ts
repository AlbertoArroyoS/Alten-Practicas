import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  //si el path esta vacio que se vaya al modulo autenticacion
  {path : '', loadChildren: () => import('./../auth/auth.module').then(m => m.AuthModule)},
  {path : 'book-catalog', loadChildren: () => import('./../book-catalog/book-catalog.module').then(m => m.BookCatalogModule)}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
