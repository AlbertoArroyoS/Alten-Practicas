import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BookCatalogLayoutComponent } from './layout/book-catalog-layout/book-catalog-layout.component';
import { BookshopComponent } from './pages/bookshop/bookshop.component';

const routes: Routes = [
  //el children lo utilizo para derfinir las rutas secundarias
  {path: '', component : BookCatalogLayoutComponent, children : [
    {path: 'book', component: BookshopComponent},
  ]
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BookCatalogRoutingModule { }
