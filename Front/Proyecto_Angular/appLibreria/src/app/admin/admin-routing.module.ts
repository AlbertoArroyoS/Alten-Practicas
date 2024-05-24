import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListUserComponent } from './pages/user/list-user/list-user.component';
import { AddUserComponent } from './pages/user/add-user/add-user.component';
import { AddAdminComponent } from './pages/user/add-admin/add-admin.component';
import { AdminUserComponent } from './admin-user/admin-user.component';

const routes: Routes = [ 

    { path: '', component: AdminUserComponent,
    children: [
      { path: 'list-user', component: ListUserComponent, title: 'Lista de usuarios' },
      { path: 'add-user', component: AddUserComponent, title: 'Nuevo usuario' },
      { path: 'add-admin', component: AddAdminComponent, title: 'Nuevo administrador' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
