import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/autores';

  constructor(
    private HttpClient: HttpClient
  ) { }


  public getAllAuthors(): Observable<any> {
    return this.HttpClient.get(this.API_SERVER + 'authors');
  }
}
