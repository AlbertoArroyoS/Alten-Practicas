import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ReportRoutingModule } from './report-routing.module';
import { ReportLayoutComponent } from './layout/report-layout/report-layout.component';


@NgModule({
  declarations: [
    ReportLayoutComponent
  ],
  imports: [
    CommonModule,
    ReportRoutingModule
  ]
})
export class ReportModule { }
