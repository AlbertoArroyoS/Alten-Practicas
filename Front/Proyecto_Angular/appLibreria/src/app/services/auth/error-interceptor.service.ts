import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

/**
 * Servicio que implementa HttpInterceptor para interceptar solicitudes HTTP
 * y manejar errores globalmente.
 */
@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor {

  /**
   * Constructor vacío ya que no se necesitan servicios inyectados en este interceptor.
   */
  constructor() { }

  /**
   * Método intercept que se llama automáticamente en cada solicitud HTTP.
   * Intercepta la solicitud HTTP y maneja los errores globalmente.
   * @param req La solicitud HTTP original.
   * @param next El siguiente manejador en la cadena de interceptores.
   * @returns Un Observable de HttpEvent.
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Pasa la solicitud al siguiente manejador y añade un manejador de errores con el operador pipe
    return next.handle(req).pipe(
      // Utiliza el operador catchError para interceptar y manejar errores
      catchError(error => {
        // Imprime el error en la consola para propósitos de depuración
        console.error(error);
        // Retorna el error usando throwError para que pueda ser manejado por el llamador
        return throwError(() => error);
      })
    );
  }
}
