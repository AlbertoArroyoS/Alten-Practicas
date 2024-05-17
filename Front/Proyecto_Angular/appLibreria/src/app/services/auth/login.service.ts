import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { CookieService } from 'ngx-cookie-service';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  public currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false); // Cambiado a public
  public currentUserData: BehaviorSubject<AuthResponse | null> = new BehaviorSubject<AuthResponse | null>(null); // Cambiado a public

  private API_SERVER = 'http://localhost:8080/auth/login';

  // Variable local para almacenar los datos del usuario logueado
  private user: AuthResponse | null = null;

  constructor(private httpClient: HttpClient, private cookieService: CookieService) {
    this.initializeUser();
  }

  // Método para inicializar el usuario desde sessionStorage
  private initializeUser(): void {
    const user = sessionStorage.getItem('user');
    if (user) {
      this.user = JSON.parse(user);
      this.currentUserLoginOn.next(true);
      this.currentUserData.next(this.user);
    } else {
      this.currentUserLoginOn.next(false);
      this.currentUserData.next(null);
    }
  }

  // Método para almacenar datos del usuario en sessionStorage
  private storeUser(userData: AuthResponse): void {
    sessionStorage.setItem('token', userData.token);
    sessionStorage.setItem('idUsuario', userData.idUsuario.toString());
    sessionStorage.setItem('username', userData.username);
    sessionStorage.setItem('user', JSON.stringify(userData));
    this.user = userData;
    this.currentUserData.next(userData);
    this.currentUserLoginOn.next(true);
  }

  loginSpring(credentials: LoginRequest): Observable<AuthResponse> {
    return this.httpClient.post<AuthResponse>(this.API_SERVER, credentials).pipe(
      tap((userData) => {
        this.storeUser(userData);
      }),
      catchError(this.handleError)
    );
  }

  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('idUsuario');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('user');
    this.currentUserLoginOn.next(false);
    this.currentUserData.next(null);
    this.user = null;
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error:', error.error);
    } else {
      console.error('Backend retornó el código de estado', error.status, error.error);
    }
    return throwError(() => new Error('Algo falló. Por favor intente nuevamente.'));
  }

  // Métodos para obtener los valores de los BehaviorSubjects
  get userData(): Observable<AuthResponse | null> {
    return this.currentUserData.asObservable();
  }

  get userLoginOn(): Observable<boolean> {
    return this.currentUserLoginOn.asObservable();
  }

  // Métodos para acceder a la variable local del usuario
  getUserToken(): string | null {
    return this.user ? this.user.token : null;
  }

  getUserId(): number | null {
    return this.user ? this.user.idUsuario : null;
  }

  getUserName(): string | null {
    return this.user ? this.user.username : null;
  }

  // Método para obtener todo el objeto del usuario
  getUser(): AuthResponse | null {
    return this.user;
  }
}
