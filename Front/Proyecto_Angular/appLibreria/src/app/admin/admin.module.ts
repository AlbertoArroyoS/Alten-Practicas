import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { UserComponent } from './pages/user/user/user.component';
import { ListUserComponent } from './pages/user/list-user/list-user.component';
import { UserModule } from '../user/user.module';


@NgModule({
  declarations: [
    UserComponent,
    ListUserComponent
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    UserModule
  ]
})
export class AdminModule { }
