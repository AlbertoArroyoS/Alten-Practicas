import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CorePublicLayoutComponent } from './layout/core-public-layout/core-public-layout.component';
import { CorePrivateLayoutComponent } from './layout/core-private-layout/core-private-layout.component';
import { AuthGuard } from './../guards/authGuard.guard';
import { RoleGuard } from './../guards/roleGuard.guard';

const routes: Routes = [
  { 
    path: '', 
    component: CorePublicLayoutComponent,
    loadChildren: () => import('../auth/auth.module').then(m => m.AuthModule) 
  },
  { 
    path: 'book-catalog',
    component: CorePrivateLayoutComponent,
    loadChildren: () => import('../book-catalog/book-catalog.module').then(m => m.BookCatalogModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { allowedRoles: ['USER'] } // Pasamos los roles esperados como datos
  },
  {
    path: 'report',
    component: CorePrivateLayoutComponent,
    loadChildren: () => import('../report/report.module').then(m => m.ReportModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { allowedRoles: ['ADMIN'] }
  },
  {
    path: 'security',
    component: CorePrivateLayoutComponent,
    loadChildren: () => import('../security/security.module').then(m => m.SecurityModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { allowedRoles: ['ADMIN'] }
  },
  {
    path: 'dashboard',
    component: CorePrivateLayoutComponent,
    loadChildren: () => import('../dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { allowedRoles: ['USER', 'ADMIN'] }
  },
  {
    path: 'user',
    component: CorePrivateLayoutComponent,
    loadChildren: () => import('../user/user.module').then(m => m.UserModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { allowedRoles: ['ADMIN']  }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
