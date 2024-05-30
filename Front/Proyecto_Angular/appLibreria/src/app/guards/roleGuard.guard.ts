import { Injectable, OnDestroy } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree, CanLoad, Route, UrlSegment } from '@angular/router';
import { Observable, of, Subject } from 'rxjs';
import { map, catchError, takeUntil } from 'rxjs/operators';
import { LoginService } from '../services/auth/login.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate, OnDestroy, CanLoad {
  private destroy$ = new Subject<void>();

  constructor(private loginService: LoginService, private router: Router) {}
  canLoad(route: Route, segments: UrlSegment[]): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
    return this.hasRole(route);
  }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Observable<boolean | UrlTree> {
    return this.hasRole(route);
  }

  private hasRole(route: ActivatedRouteSnapshot | Route) {
    const allowedRoles = route.data?.['allowedRoles'] ?? [];

    return this.loginService.user$.pipe(
      takeUntil(this.destroy$),
      map(user => {
        const userRole = user ? user.role : "";
        console.log('******** Rol del usuario:', userRole);
        if (user && allowedRoles.includes(userRole)) {
          return true;
        } else {
          alert(`Acceso Denegado. Su rol actual es: ${userRole ? userRole : 'No Autenticado'}`);
          return this.router.createUrlTree(['/dasboard']);
        }
      }),
      catchError(() => {
        console.error('Error en la obtención del usuario o en la verificación del rol');
        return of(this.router.createUrlTree(['/sign-in']));
      })
    );
  }

  ngOnDestroy() {
    console.log('RoleGuard destruido, cancelando suscripciones');
    this.destroy$.next();
    this.destroy$.complete();
  }
}