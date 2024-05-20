import { Routes } from '@angular/router';
import { AuthLayoutComponent } from './layout/auth-layout/auth-layout.component';
import { SignInComponent } from './pages/sign-in/sign-in.component';
import { SignOutComponent } from './pages/sign-out/sign-out.component';


export const authRoutes: Routes = [
  {
    path: '',
    component: AuthLayoutComponent,
    children: [
      { path: 'sing-in', component: SignInComponent, title: 'Iniciar Sessión' },
      { path: '', redirectTo: 'sing-in', pathMatch: 'full' },
      { path: 'sing-out', component: SignOutComponent },
    ],
  },
];