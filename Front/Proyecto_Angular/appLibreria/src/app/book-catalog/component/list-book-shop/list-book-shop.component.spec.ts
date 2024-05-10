import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListBookShopComponent } from './list-book-shop.component';

describe('ListBookShopComponent', () => {
  let component: ListBookShopComponent;
  let fixture: ComponentFixture<ListBookShopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListBookShopComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListBookShopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
