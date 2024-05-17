import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root'
})
export class JwtInterceptorService implements HttpInterceptor {

  // El constructor inyecta el LoginService, que se utiliza para obtener el token JWT del usuario logueado
  constructor(private loginService: LoginService) { }

  // El método intercept se llama automáticamente en cada solicitud HTTP
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Obtiene el token JWT del LoginService usando el método getUserToken
    const token: string | null = this.loginService.getUserToken();

    // Si el token no está vacío, clona la solicitud y añade el token en los encabezados
    if (token) {
      req = req.clone({
        //settear el token en el encabezado de autorización
        setHeaders: {
          // Establece el tipo de contenido como JSON
          'Content-Type': 'application/json; charset=utf-8',
          // Indica que la respuesta esperada es JSON
          'Accept': 'application/json',
          // Añade el token JWT en el encabezado de autorización
          'Authorization': `Bearer ${token}`,
        },
      });
    }

    // Pasa la solicitud (modificada o no) al siguiente manejador en la cadena
    return next.handle(req);
  }
}
