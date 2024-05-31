import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

/**
 * Servicio que implementa HttpInterceptor para interceptar solicitudes HTTP
 * y añadir el token JWT en los encabezados de autorización.
 */
@Injectable({
  providedIn: 'root'
})
export class JwtInterceptorService implements HttpInterceptor {

  /**
   * Constructor que inyecta el LoginService.
   * @param loginService Servicio de autenticación para obtener el token JWT.
   */
  constructor(private loginService: LoginService) { }

  /**
   * Método intercept que se llama automáticamente en cada solicitud HTTP.
   * Intercepta la solicitud HTTP, añade el token JWT si está disponible y pasa la solicitud al siguiente manejador.
   * @param req La solicitud HTTP original.
   * @param next El siguiente manejador en la cadena de interceptores.
   * @returns Un Observable de HttpEvent.
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Obtiene el token JWT del LoginService
    const token: string | null = this.loginService.getUserToken();

    // Si el token está presente, clona la solicitud y añade el token en los encabezados
    if (token) {
      req = req.clone({
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
