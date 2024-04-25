import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import { SignOutComponent } from './pages/sign-out/sign-out.component';

const routes: Routes = [
  //Carga el componente donde se renderiza todo
  {path : '', component: AuthLayoutComponent, children: [
    {path: 'sign-in', component : SignInComponent},
    //si el path esta vacio que haga redireccion al sign-in
    {path: '', redirectTo: 'sign-in', pathMatch: 'full'},
    //para cerrar sesion
    {path: 'sign-out', component : SignOutComponent},
  ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
