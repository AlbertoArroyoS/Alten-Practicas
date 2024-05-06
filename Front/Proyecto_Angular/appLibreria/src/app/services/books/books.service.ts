import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/libros';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/libros/libro';

  constructor(
    private httpClient: HttpClient
  ) { }


  public getAllBooks(): Observable<any> {
    return this.httpClient.get(this.API_SERVER);
  }

  // Método para obtener un libro por su ID
  
  public getBookById(bookId: number): Observable<any> {
    const url = `${this.API_SERVER}/${bookId.toString()}`; // Convertir bookId a string
    return this.httpClient.get(url); 
  }
}
