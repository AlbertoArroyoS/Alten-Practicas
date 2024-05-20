import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';

/**
 * Servicio de autenticación que maneja el estado de autenticación del usuario,
 * el almacenamiento de tokens y la gestión de la sesión.
 */
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  /**
   * BehaviorSubject para mantener el estado del usuario logueado.
   */
  public currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  
  /**
   * BehaviorSubject para mantener los datos del usuario logueado.
   */
  public currentUserData: BehaviorSubject<AuthResponse | null> = new BehaviorSubject<AuthResponse | null>(null);

  /**
   * URL del servidor API.
   */
  private API_SERVER = 'http://localhost:8080/auth/login';

  /**
   * Variable local para almacenar los datos del usuario logueado.
   */
  private user: AuthResponse | null = null;

  /**
   * Constructor que inyecta las dependencias necesarias.
   * @param httpClient HttpClient para realizar solicitudes HTTP.
   */
  constructor(private httpClient: HttpClient) {
    // Inicializa el usuario desde sessionStorage si está presente.
    this.initializeUser();
  }

  /**
   * Inicializa el usuario desde sessionStorage si los datos están presentes.
   */
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

  /**
   * Almacena los datos del usuario en sessionStorage y actualiza los BehaviorSubjects.
   * @param userData Datos de autenticación del usuario.
   */
  private storeUser(userData: AuthResponse): void {
    sessionStorage.setItem('token', userData.token);
    sessionStorage.setItem('idUsuario', userData.idUsuario.toString());
    sessionStorage.setItem('username', userData.username);
    sessionStorage.setItem('user', JSON.stringify(userData));
    this.user = userData;
    this.currentUserData.next(userData);
    this.currentUserLoginOn.next(true);
  }

  /**
   * Realiza la solicitud de login al servidor y almacena los datos del usuario si la autenticación es exitosa.
   * @param credentials Credenciales de login del usuario.
   * @returns Un Observable con la respuesta de autenticación.
   */
  loginSpring(credentials: LoginRequest): Observable<AuthResponse> {
    return this.httpClient.post<AuthResponse>(this.API_SERVER, credentials).pipe(
      tap((userData) => {
        this.storeUser(userData);
      }),
      catchError(this.handleError)
    );
  }

  /**
   * Realiza el logout del usuario eliminando los datos del usuario de sessionStorage
   * y actualizando los BehaviorSubjects.
   */
  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('idUsuario');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('user');
    this.currentUserLoginOn.next(false);
    this.currentUserData.next(null);
    this.user = null;
  }

  /**
   * Maneja los errores de las solicitudes HTTP.
   * @param error El error HTTP.
   * @returns Un Observable que arroja un error.
   */
  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error:', error.error);
    } else {
      console.error('Backend retornó el código de estado', error.status, error.error);
    }
    return throwError(() => new Error('Algo falló. Por favor intente nuevamente.'));
  }

  /**
   * Obtiene los datos del usuario como un Observable.
   * @returns Un Observable con los datos de autenticación del usuario.
   */
  get userData(): Observable<AuthResponse | null> {
    return this.currentUserData.asObservable();
  }

  /**
   * Obtiene el estado de login del usuario como un Observable.
   * @returns Un Observable con el estado de autenticación del usuario.
   */
  get userLoginOn(): Observable<boolean> {
    return this.currentUserLoginOn.asObservable();
  }

  /**
   * Obtiene el token del usuario autenticado.
   * @returns El token del usuario o null si no está autenticado.
   */
  getUserToken(): string | null {
    return this.user ? this.user.token : null;
  }

  /**
   * Obtiene el ID del usuario autenticado.
   * @returns El ID del usuario o null si no está autenticado.
   */
  getUserId(): number | null {
    return this.user ? this.user.idUsuario : null;
  }

  /**
   * Obtiene el nombre de usuario del usuario autenticado.
   * @returns El nombre de usuario o null si no está autenticado.
   */
  getUserName(): string | null {
    return this.user ? this.user.username : null;
  }

  /**
   * Obtiene el objeto completo del usuario autenticado.
   * @returns Los datos del usuario o null si no está autenticado.
   */
  getUser(): AuthResponse | null {
    return this.user;
  }
}
