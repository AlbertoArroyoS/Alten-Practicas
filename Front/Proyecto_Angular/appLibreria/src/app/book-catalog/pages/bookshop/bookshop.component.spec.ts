import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookshopComponent } from './bookshop.component';

describe('BookshopComponent', () => {
  let component: BookshopComponent;
  let fixture: ComponentFixture<BookshopComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookshopComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookshopComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
