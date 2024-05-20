import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { LoginService } from 'src/app/services/auth/login.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    //console.log('Intentando acceder a ruta:', state.url);
    
    // Verifica si el usuario está logueado utilizando un BehaviorSubject y dirige a la página de login si no lo está
    return this.loginService.userLoginOn$.pipe(
      map((isLoggedIn) => isLoggedIn || this.router.createUrlTree(['/sign-in']))
    );
  }

}
