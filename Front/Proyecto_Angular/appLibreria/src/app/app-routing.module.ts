import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CoreLayoutComponent } from './core/layout/core-layout/core-layout.component';

const routes: Routes = [
  {path:'', component:CoreLayoutComponent, loadChildren: () => import('./core/core.module').then(m => m.CoreModule)},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
