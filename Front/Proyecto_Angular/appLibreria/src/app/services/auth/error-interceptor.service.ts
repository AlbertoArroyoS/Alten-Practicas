import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorInterceptorService implements HttpInterceptor {

  // El constructor está vacío porque no necesitamos inyectar servicios en este interceptor
  constructor() { }

  // El método intercept se llama automáticamente en cada solicitud HTTP
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