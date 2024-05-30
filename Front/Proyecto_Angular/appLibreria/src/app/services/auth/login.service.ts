import { switchMap, catchError, tap } from 'rxjs/operators';
import { Observable, ReplaySubject, of, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  private API_SERVER_LOGIN = 'http://localhost:8080/auth/login';
  private API_SERVER_USER_DETAILS = 'http://localhost:8080/v1/app-libreria/usuarios/usuario';

  private currentUserSubject: ReplaySubject<any> = new ReplaySubject<any>(1);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor(private httpClient: HttpClient, private router: Router) {}

  loginSpring(credentials: LoginRequest): Observable<any> {
    return this.httpClient.post<AuthResponse>(this.API_SERVER_LOGIN, credentials).pipe(
      switchMap(authResponse => {
        if (!authResponse.idUsuario) {
          throwError(() => new Error('Login failed, no user ID provided'));
        }
        return this.httpClient.get<UserRequest>(`${this.API_SERVER_USER_DETAILS}/${authResponse.idUsuario}`);
      }),
      tap(fullUserData => {
        this.storeUser(fullUserData);
        this.redirectToDashboard();
      }),
      catchError(this.handleError)
    );
  }

  private storeUser(userData: any): void {
    sessionStorage.setItem('currentUser', JSON.stringify(userData));
    this.currentUserSubject.next(userData);
  }

  private redirectToDashboard(): void {
    this.router.navigateByUrl('/dashboard');
  }

  private handleError(error: HttpErrorResponse): Observable<never> {
    console.error('Error:', error.message);
    return throwError(() => new Error('An error occurred, please try again later.'));
  }
}
