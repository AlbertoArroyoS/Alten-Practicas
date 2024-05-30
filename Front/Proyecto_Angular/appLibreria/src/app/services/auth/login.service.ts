import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { AuthResponse } from 'src/app/shared/model/response/authResponse';
import { Router } from '@angular/router';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

/**
 * Servicio de autenticación que maneja el estado de autenticación del usuario,
 * el almacenamiento de tokens y la gestión de la sesión.
 */
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  /**
   * ReplaySubject para mantener los datos del usuario logueado.
   * Emitirá el último valor inmediatamente a cualquier suscriptor nuevo.
   */
  private userSubject: ReplaySubject<AuthResponse | null> = new ReplaySubject<AuthResponse | null>(1);
  
  /**
   * Observable que expone los datos del usuario.
   */
  private currentUser: AuthResponse | null = null;

  private currentUserSubject = new BehaviorSubject<UserRequest | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();
  public user$: Observable<UserRequest | null>;

  
  /**
   * Observable que indica si el usuario está logueado.
   */
  public userLoginOn$: Observable<boolean> = this.user$.pipe(map(user => !!user));


  /**
   * URL del servidor API.
   */
  private API_SERVER = 'http://localhost:8080/auth/login';

  /**
   * Variable local para almacenar los datos del usuario logueado.
   */
  

  setCurrentUser(user: UserRequest): void {
    this.currentUserSubject.next(user);
  }
  

  /**
   * Constructor que inyecta las dependencias necesarias.
   * @param httpClient HttpClient para realizar solicitudes HTTP.
   * @param router Router para navegar después del login/logout.
   */
  constructor(private httpClient: HttpClient, private router: Router) {
    // Inicializa el usuario desde sessionStorage si está presente.
    this.user$ = new Observable<UserRequest | null>();
    this.loadUserFromSessionStorage();
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
        //console.log('Usuario logueado:', userData); // Agrega este console.log para mostrar el usuario
      }),
      catchError(this.handleError)
    );
  }

  /**
   * Realiza el logout del usuario eliminando los datos del usuario de sessionStorage
   * y actualizando los ReplaySubjects.
   */
  logout(): void {
    this.removeUserFromSessionStorage();
    this.currentUser = null;
    this.userSubject.next(this.currentUser);
    //console.log('Usuario que sale:', this.currentUser)
    //console.log('Usuario que sale2:', this.userSubject)
    //this.router.navigate(['/sign-in']);
  }
  

  /**
   * Redirige al usuario al dashboard después del login exitoso.
   */
  private redirectToDashboard(): void {
    this.router.navigateByUrl('/dashboard');
  }

  /**
   * Almacena los datos del usuario en sessionStorage y actualiza los ReplaySubjects.
   * @param userData Datos de autenticación del usuario.
   */
  private storeUser(userData: AuthResponse): void {
    sessionStorage.setItem('token', userData.token);
    sessionStorage.setItem('idUsuario', userData.idUsuario.toString());
    sessionStorage.setItem('username', userData.username);
    sessionStorage.setItem('role', userData.role);
    sessionStorage.setItem('user', JSON.stringify(userData));
    this.currentUser = userData;
    this.userSubject.next(this.currentUser);
  }

  /**
   * Carga los datos del usuario desde sessionStorage si los datos están presentes.
   */
  private loadUserFromSessionStorage(): void {
    const user = sessionStorage.getItem('user');
    if (user) {
      this.currentUser = JSON.parse(user) as AuthResponse;
      this.userSubject.next(this.currentUser);
    } else {
      this.currentUser = null;
      this.userSubject.next(this.currentUser);
    }
  }

  /**
   * Elimina los datos del usuario de sessionStorage.
   */
  private removeUserFromSessionStorage(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('idUsuario');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('user');
  }

  /**
   * Maneja los errores de las solicitudes HTTP.
   * @param error El error HTTP.
   * @returns Un Observable que arroja un error.
   */
  private handleError(error: HttpErrorResponse): Observable<never> {
    if (error.status === 0) {
      console.error('Se ha producido un error:', error.error);
    } else {
      console.error('Backend retornó el código de estado', error.status, error.error);
    }
    return throwError(() => new Error('Algo falló. Por favor intente nuevamente.'));
  }

  /**
   * Obtiene el token del usuario autenticado.
   * @returns El token del usuario o null si no está autenticado.
   */
  getUserToken(): string | null {
    return this.currentUser ? this.currentUser.token : null;
  }

  /**
   * Obtiene el ID del usuario autenticado.
   * @returns El ID del usuario o null si no está autenticado.
   */
  getUserId(): number | null {
    return this.currentUser ? this.currentUser.idUsuario : null;
  }

  /**
   * Obtiene el nombre de usuario del usuario autenticado.
   * @returns El nombre de usuario o null si no está autenticado.
   */
  getUserName(): string | null {
    return this.currentUser ? this.currentUser.username : null;
  }

  /**
   * Obtiene el rol del usuario autenticado.
   * @returns El rol del usuario o null si no está autenticado.
   */
  getUserRole(): string | null {
    return this.currentUser ? this.currentUser.role : null;
  }

  /**
   * Obtiene el objeto completo del usuario autenticado.
   * @returns Los datos del usuario o null si no está autenticado.
   */
  getUser(): AuthResponse | null {
    return this.currentUser;
  }

  // Método para acceder al usuario completo públicamente si es necesario
  public getCurrentUser(): AuthResponse | null {
    return this.currentUser;
  }
}