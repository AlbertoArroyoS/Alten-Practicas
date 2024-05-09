import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/libros';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/libros/libro';
  private SEARCH_ENDPOINT = 'libro'; 

  constructor(
    private httpClient: HttpClient
  ) { }


  public getAllBooks(page: number, size: number): Observable<any> {
    //return this.httpClient.get(this.API_SERVER);
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.httpClient.get(`${this.API_SERVER}`, { params: params });
  }

  // Método para obtener un libro por su ID
  
  public getBookById(bookId: number): Observable<any> {
    const url = `${this.API_SERVER}/${bookId.toString()}`; // Convertir bookId a string
    return this.httpClient.get(this.API_SERVER2+'/'+bookId.toString()); 
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
  public addBook(book: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER2, book);
  }
}
