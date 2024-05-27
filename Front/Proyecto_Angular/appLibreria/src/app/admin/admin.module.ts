import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { ListUserComponent } from './pages/user/list-user/list-user.component';
import { UserModule } from '../user/user.module';
import { AddUserComponent } from './pages/user/add-user/add-user.component';
import { AddAdminComponent } from './pages/user/add-admin/add-admin.component';
import { AdminUserComponent } from './admin-user/admin-user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';


@NgModule({
  declarations: [
  
    AddUserComponent,
       AddAdminComponent,
       AdminUserComponent,
      ListUserComponent,
      

  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    UserModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ]
})
export class AdminModule { }
