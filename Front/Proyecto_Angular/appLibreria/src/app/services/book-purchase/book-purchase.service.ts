import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BookPurchaseService {

  private API_SERVER = 'http://localhost:8080/v1/app-libreria/compras';
  private API_SERVER2 = 'http://localhost:8080/v1/app-libreria/compras/compra';
  private SEARCH_ENDPOINT = 'libro'; 

  constructor(
    private httpClient: HttpClient
  ) { }

  public getAllBooksPurchases(page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.httpClient.get(`${this.API_SERVER}`, { params: params });
  }

  public getClientPurchases(idCliente: number, page: number, size: number): Observable<any> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.httpClient.get(`${this.API_SERVER2}/cliente/${idCliente}`, { params: params });
  }

  public purchaseBook(purchaseData: { fechaCompra: string, precio: number, idCliente: number, idLibro: number }): Observable<any> {
    return this.httpClient.post(`${this.API_SERVER2}`, purchaseData);
  }
}