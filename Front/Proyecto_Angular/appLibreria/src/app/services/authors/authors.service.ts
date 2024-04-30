import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthorsService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/autores';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/autores/autor';

  constructor(
    private HttpClient: HttpClient
  ) { }


  public getAllAuthors(): Observable<any> {
    return this.HttpClient.get(this.API_SERVER);
  }

  public addAuthor(author: any): Observable<any> {
    return this.HttpClient.post(this.API_SERVER2, author);
  }
}
