import { HttpClientModule } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthorsService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/autores';

  constructor(
    private HttpClient: HttpClient
  ) { }


  public getAllAuthors(): Observable<any> {
    return this.HttpClient.get(this.API_SERVER);
  }
}
