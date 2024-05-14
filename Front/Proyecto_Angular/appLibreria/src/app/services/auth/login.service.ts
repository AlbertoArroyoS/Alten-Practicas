import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError,BehaviorSubject  } from 'rxjs';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { UserRequest } from 'src/app/shared/model/request/userRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  //declarar dos BehaviorSubject para el estado de la sesión y los datos del usuario, de inicio en falso
  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  //informacion del usuario como la va a devolver la api, de inicio vacio
  currentUserData: BehaviorSubject<UserRequest> =new BehaviorSubject<UserRequest>({id:0, email:''});

  constructor(private http: HttpClient) { }

  loginSpring(credentials:LoginRequest):Observable<UserRequest>{
    return this.http.get<UserRequest>('././assets/data.json').pipe(
      tap( (userData: UserRequest) => { //tap es un operador que permite realizar acciones secundarias en el flujo de datos, mientras se mantiene el flujo de datos
        //actualizar el estado de la sesión y los datos del usuario
        this.currentUserData.next(userData);
        //cambiar el estado de la sesión a verdadero
        this.currentUserLoginOn.next(true);
      }),
      catchError(this.handleError)
    );
  }
  //metodo para manejar errores
  private handleError(error:HttpErrorResponse){
    if(error.status===0){
      console.error('Se ha producio un error ', error.error);
    }
    else{
      console.error('Backend retornó el código de estado ', error.status, error.error);
    }
    return throwError(()=> new Error('Algo falló. Por favor intente nuevamente.'));
  }
  //crear las propiedades para que los valores se los behaviorSubject se puedan suscribir
 //metodos para obtener los valores de los BehaviorSubject
  get userData():Observable<UserRequest>{
    return this.currentUserData.asObservable();
  }

  get userLoginOn(): Observable<boolean>{
    return this.currentUserLoginOn.asObservable();
  }

}
