import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserDetailsComponent } from './user-details/user-details.component';
import { ListUserComponent } from './list-user/list-user.component';
import { UserComponent } from '../admin/pages/user/user.component';

const routes: Routes = [
  { path: '', component: UserComponent,
  children: [
    { path: 'user', component: UserDetailsComponent, title: 'Usuario' },
    { path: 'user/list-user', component: ListUserComponent, title: 'Lista de Usuarios' }
  ]
   }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
