import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/libros';
  //private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/libros/libro';
  private SEARCH_ENDPOINT = 'libro'; 

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

  // Método para buscar libros por palabra clave
  
  public searchBooksByKeyword(keyword: string, page: number = 0, size: number = 10, field: string = 'titulo', order: number = 1): Observable<any> {
    const url = `${this.API_SERVER}/${this.SEARCH_ENDPOINT}?key_word=${keyword}&page=${page}&size=${size}&field=${field}&order=${order}`;
    return this.httpClient.get(url);
  }
  /*
  public searchBooksByKeyword(keyword: string, page: number = 0, size: number = 10, field: string = 'titulo', order: number = 1): Observable<any> {
    const url = `${this.API_SERVER}/${this.SEARCH_ENDPOINT}?key_word=${keyword}&page=${page}&size=${size}&field=${field}&order=${order}`;
    return this.httpClient.get(url);
  }*/

  //http://localhost:8080/v1/app-libreria/libros/libro?key_word=cien&page=0&size=1&field=titulo&order=1
}
