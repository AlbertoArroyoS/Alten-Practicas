import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  //si el path esta vacio que se vaya al modulo autenticacion
  {path : '', loadChildren: () => import('./../auth/auth.module').then(m => m.AuthModule)}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
