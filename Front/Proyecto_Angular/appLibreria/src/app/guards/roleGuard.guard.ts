import { Injectable } from '@angular/core';
import { CanLoad, Route, UrlSegment, Router, UrlTree } from '@angular/router';
import { Observable, of } from 'rxjs';
import { map, catchError, take } from 'rxjs/operators';
import { LoginService } from '../services/auth/login.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanLoad {
  constructor(private loginService: LoginService, private router: Router) {}

  canLoad(
    route: Route,
    segments: UrlSegment[]
  ): Observable<boolean | UrlTree> {
    const allowedRoles = route.data?.['allowedRoles'];

    return this.loginService.user$.pipe(
      take(1), // Toma solo el primer valor emitido para evitar múltiples suscripciones
      map(user => {
        // Utiliza getUserRole() para obtener el rol del usuario
        const userRole = this.loginService.getUserRole();
        if (user && allowedRoles.includes(userRole)) {
          return true; // Permite la carga del módulo si el usuario tiene el rol adecuado
        } else {
          // Alerta al usuario y redirecciona si no tiene el rol necesario
          alert(`Acceso Denegado. Su rol actual es: ${userRole ? userRole : 'No Autenticado'}`);
          return this.router.createUrlTree(['/unauthorized']); // Redirige a una página no autorizada
        }
      }),
      catchError(() => of(this.router.createUrlTree(['/sign-in']))) // En caso de error, redirige al login
    );
  }
}
