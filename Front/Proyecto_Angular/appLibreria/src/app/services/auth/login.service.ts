import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http: HttpClient) { }

  loginSpring(credentials:LoginRequest):Observable<UserRequest>{
    return this.http.get<UserRequest>('././assets/data.json').pipe(
      tap( (userData: UserRequest) => {
        //this.currentUserData.next(userData);
        //this.currentUserLoginOn.next(true);
      }),
      catchError(this.handleError)
    );
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
