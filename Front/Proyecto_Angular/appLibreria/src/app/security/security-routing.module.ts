import { ChangePasswordComponent } from './pages/change-password/change-password.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SecurityLayoutComponent } from './layout/security-layout/security-layout.component';
import { UserComponent } from './pages/user/user.component';
import { RoleComponent } from './pages/role/role.component';

const routes: Routes = [
  {
    path: '',
    component: SecurityLayoutComponent,
    children: [
      { path: 'user', component: UserComponent },
      //{path: '', redirectTo: 'book', pathMatch: 'full'},
      { path: 'role', component: RoleComponent },
      { path: 'change-password', component: ChangePasswordComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SecurityRoutingModule {}
