import { HttpErrorResponse } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Subscription } from 'rxjs';
import { BookShopService } from 'src/app/services/book-shop/book-shop.service';

@Component({
  selector: 'app-list-book-shop',
  templateUrl: './list-book-shop.component.html',
  styleUrls: ['./list-book-shop.component.scss']
})
export class ListBookShopComponent {
  protected subscription: Array<Subscription> = new Array(); // Array de suscripciones
  
  constructor(
    public ventaLibroService: BookShopService,
  ){

  }



}
