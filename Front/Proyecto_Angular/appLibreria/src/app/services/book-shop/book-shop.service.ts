import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BookToShopLibrary } from 'src/app/shared/model/request/bookToShopLibrary';

@Injectable({
  providedIn: 'root'
})
export class BookShopService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/libreria_libros';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/libreria_libros/libreria_libro';
  private SEARCH_ENDPOINT = 'libro'; 

  constructor(
    private httpClient: HttpClient
  ) { }

  public getAllBooksShell(page: number, size: number): Observable<any> {
    //return this.httpClient.get(this.API_SERVER);
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.httpClient.get(`${this.API_SERVER}`, { params: params });
  }

  public addBookToLibrary(book: BookToShopLibrary): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    return this.httpClient.post(this.API_SERVER2, book, { headers: headers });
  }

  // Método para buscar libros por título
  public searchBooksByTitle(titulo: string, page: number, size: number): Observable<any> {
    let params = new HttpParams().set('titulo', titulo).set('page', page.toString()).set('size', size.toString());
    return this.httpClient.get(`${this.API_SERVER2}`, { params: params });
  }

  public getBooksByIdLibreria(libraryId: number, page: number, size: number): Observable<any> {
    //return this.httpClient.get(this.API_SERVER);
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.httpClient.get(`${this.API_SERVER}/libreria/${libraryId}`, { params: params });
  }

  getBooksExcludingLibreria(idLibreria: number, page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.httpClient.get<any>(`${this.API_SERVER}/libreria/not/${idLibreria}`, { params: params });
  }


}
