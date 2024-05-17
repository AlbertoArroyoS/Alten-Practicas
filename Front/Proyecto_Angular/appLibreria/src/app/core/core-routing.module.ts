import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorePublicLayoutComponent } from './layout/core-public-layout/core-public-layout.component';
import { CorePrivateLayoutComponent } from './layout/core-private-layout/core-private-layout.component';

const routes: Routes = [
  //si el path esta vacio que se vaya al modulo autenticacion
  {path : '',component: CorePublicLayoutComponent ,loadChildren: () => import('./../auth/auth.module').then(m => m.AuthModule)},
  {path : 'book-catalog',component: CorePrivateLayoutComponent, loadChildren: () => import('./../book-catalog/book-catalog.module').then(m => m.BookCatalogModule)},
  {path : 'report',component: CorePrivateLayoutComponent, loadChildren: () => import('./../report/report.module').then(m => m.ReportModule)},
  {path : 'security',component: CorePrivateLayoutComponent, loadChildren: () => import('./../security/security.module').then(m => m.SecurityModule)},
  {path: 'dashboard', component: CorePrivateLayoutComponent, loadChildren: () => import('./../dashboard/dashboard.module').then(m => m.DashboardModule)}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
