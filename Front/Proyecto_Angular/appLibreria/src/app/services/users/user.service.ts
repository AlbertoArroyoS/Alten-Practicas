import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/usuarios';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/usuarios/usuario/';

  constructor(private httpClient:HttpClient) { }

  getUser(id:number):Observable<UserRequest>{

    return this.httpClient.get<UserRequest>(this.API_SERVER2+id).pipe(
      catchError(this.handleError)
    )
  }
/*
  updateUser(userRequest:UserRequest):Observable<any>
  {
    return this.httpClient.put(environment.urlApi+"user", userRequest).pipe(
      catchError(this.handleError)
    )
  }*/

  public updateUser(userId: number, userData: any): Observable<any> {
    const url = `${this.API_SERVER2}/${userId}`;
    return this.httpClient.put(url, userData).pipe(
      catchError(this.handleError)
    )
  }

  private handleError(error:HttpErrorResponse){
    if(error.status===0){
      console.error('Se ha producio un error ', error.error);
    }
    else{
      console.error('Backend retornó el código de estado ', error.status, error.error);
    }
    return throwError(()=> new Error('Algo falló. Por favor intente nuevamente.'));
  }

}
