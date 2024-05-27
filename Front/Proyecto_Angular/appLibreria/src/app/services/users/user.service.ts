import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { UserRequest } from 'src/app/shared/model/request/userRequest';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_SERVER = 'http://localhost:8080/v1/app-libreria/usuarios';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/usuarios/usuario';
  private API_SERVER_REGISTER_ADMIN = 'http://localhost:8080/auth/register/admins';
  private API_SERVER_REGISTER_USER = 'http://localhost:8080/auth/register';
  private API_SERVER_UPDATE_USER = 'http://localhost:8080/auth/update/users/user';
  private API_SERVER_UPDATE_CLIENT = 'http://localhost:8080/v1/app-libreria/clientes/cliente';
  private API_SERVER_UPDATE_LIBRARY = 'http://localhost:8080/v1/app-libreria/librerias/libreria';


  constructor(private httpClient: HttpClient, private cookies: CookieService) {}

  getUser(id: number): Observable<UserRequest> {
    return this.httpClient.get<UserRequest>(`${this.API_SERVER2}/${id}`).pipe(
      catchError(this.handleError)
    );
  }

  getAll(page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.httpClient.get<any>(this.API_SERVER, { params }).pipe(
      catchError(this.handleError)
    );
  }

  getAllUsers(page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.httpClient.get<any>(this.API_SERVER2+"/user", { params }).pipe(
      catchError(this.handleError)
    );
  }

  getAllAdmins(page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.httpClient.get<any>(this.API_SERVER2+"/admin", { params }).pipe(
      catchError(this.handleError)
    );
  }

  searchUsersByKeyword(keyword: string): Observable<any> {
    const url = `${this.API_SERVER}/search`;
    let params = new HttpParams().set('keyword', keyword);
    return this.httpClient.get<any>(url, { params }).pipe(
      catchError(this.handleError)
    );
  }

  addUser(user: any): Observable<any> {
    return this.httpClient.post<any>(this.API_SERVER_REGISTER_USER, user).pipe(
      catchError(this.handleError)
    );
  }

  addAdmin(user: any): Observable<any> {
    return this.httpClient.post<any>(this.API_SERVER_REGISTER_ADMIN, user).pipe(
      catchError(this.handleError)
    );
  }

  getUserById(userId: number): Observable<any> {
    const url = `${this.API_SERVER2}/${userId}`;
    return this.httpClient.get<any>(url).pipe(
      catchError(this.handleError)
    );
  }

  updateUser(userId: number, userData: any): Observable<any> {
    const url = `${this.API_SERVER_UPDATE_USER}/${userId}`;
    return this.httpClient.put<any>(url, userData).pipe(
      catchError(this.handleError)
    );
  }

  updateClient(clientId: number, userData: any): Observable<any> {
    const url = `${this.API_SERVER_UPDATE_CLIENT}/${clientId}`;
    return this.httpClient.put<any>(url, userData).pipe(
      catchError(this.handleError)
    );
  }

  updateLibrary(libraryId: number, userData: any): Observable<any> {
    const url = `${this.API_SERVER_UPDATE_LIBRARY}/${libraryId}`;
    return this.httpClient.put<any>(url, userData).pipe(
      catchError(this.handleError)
    );
  }

  deleteUserById(userId: number): Observable<any> {
    const url = `${this.API_SERVER2}/${userId}`;
    return this.httpClient.delete<any>(url).pipe(
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error:', error.error);
    } else {
      console.error('Backend retornó el código de estado:', error.status, error.error);
    }
    return throwError(() => new Error('Algo falló. Por favor intente nuevamente.'));
  }
}
