import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError,BehaviorSubject,map  } from 'rxjs';
import { LoginRequest } from 'src/app/shared/model/request/loginRequest';
import { UserRequest } from 'src/app/shared/model/request/userRequest';
import { CookieService } from "ngx-cookie-service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  //declarar dos BehaviorSubject para el estado de la sesión y los datos del usuario, de inicio en falso
  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  //informacion del usuario como la va a devolver la api, de inicio vacio
  currentUserData: BehaviorSubject<String> =new BehaviorSubject<String>("");

  // Variable local para almacenar los datos del usuario logueado
  private user: UserRequest | null = null;

  private API_SERVER = 'http://localhost:8080/auth/login';

  constructor(private httpClient: HttpClient, private cookies: CookieService) {
    //inicializar los BehaviorSubject con los valores de la sesión y los datos del usuario almacenados en el almacenamiento de sesión
    this.currentUserLoginOn=new BehaviorSubject<boolean>(sessionStorage.getItem("token")!=null);
    this.currentUserData=new BehaviorSubject<String>(sessionStorage.getItem("token") || "");   
   }

  loginSpring(credentials:LoginRequest):Observable<UserRequest>{
    return this.httpClient.post<any>(this.API_SERVER, credentials).pipe(
      tap( (userData) => { //tap es un operador que permite realizar acciones secundarias en el flujo de datos, mientras se mantiene el flujo de datos
        //guardar el token en el almacenamiento de sesión
        sessionStorage.setItem("token", userData.token);
        //actualizar el estado de la sesión y los datos del usuario
        this.currentUserData.next(userData);
        //cambiar el estado de la sesión a verdadero
        this.currentUserLoginOn.next(true);
      }),
      //mapear el resultado de la respuesta para obtener el token
      map((userData)=> userData.token),
      catchError(this.handleError)
    );
  }
  //metodo para cerrar la sesión y eliminar el token del almacenamiento de sesión
  logout():void{
    sessionStorage.removeItem("token");
    this.currentUserLoginOn.next(false);
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
  get userData():Observable<String>{
    return this.currentUserData.asObservable();
  }

  get userLoginOn(): Observable<boolean>{
    return this.currentUserLoginOn.asObservable();
  }

  get userToken():String{
    return this.currentUserData.value;
  }

  setToken(token: string) {
    this.cookies.set("token", token);
  }
  getToken() {
    return this.cookies.get("token");
  }

  


}
