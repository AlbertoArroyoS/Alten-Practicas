import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './user-routing.module';
import { UserDetailsComponent } from './user-details/user-details.component';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ListUserComponent } from './list-user/list-user.component';

@NgModule({
  declarations: [
    UserDetailsComponent,
    ListUserComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    UserDetailsComponent
  ]
})
export class UserModule { }