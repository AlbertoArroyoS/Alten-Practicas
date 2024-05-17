import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from '../security/pages/user/user.component';
import { UserDetailsComponent } from './user-details/user-details.component';

@NgModule({
  declarations: [
    UserDetailsComponent
  ],
  imports: [
    CommonModule,
    UserRoutingModule
  ],
  exports: [
    UserDetailsComponent
  ]
})
export class UserModule { }