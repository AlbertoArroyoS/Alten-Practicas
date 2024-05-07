import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams  } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthorsService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/autores';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/autores/autor';
  private SEARCH_ENDPOINT = 'autor?key_word=';

  constructor(
    private httpClient: HttpClient
  ) { }

  public getAllAuthors (): Observable<any> {
    // Construir los parámetros de la solicitud HTT
      return this.httpClient.get(this.API_SERVER);
  }


  public addAuthor(author: any): Observable<any> {
    return this.httpClient.post(this.API_SERVER2, author);
  }

  public searchAuthorsByKeyword(keyword: string): Observable<any> {
    const url = `${this.API_SERVER}/${this.SEARCH_ENDPOINT}${keyword}`; // Construye la URL con la palabra clave
    return this.httpClient.get(url);
  }

  public deleteAuthorById(authorId: number): Observable<any> {
    const url = `${this.API_SERVER2}/${authorId.toString()}`; // Convertir authorId a string
    return this.httpClient.delete(url);
  }

  public updateAuthor(authorId: number, authorData: any): Observable<any> {
    const url = `${this.API_SERVER2}/${authorId}`;
    return this.httpClient.put(url, authorData);
  }
  

}
