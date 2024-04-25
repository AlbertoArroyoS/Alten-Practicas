import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookCatalogLayoutComponent } from './book-catalog-layout.component';

describe('BookCatalogLayoutComponent', () => {
  let component: BookCatalogLayoutComponent;
  let fixture: ComponentFixture<BookCatalogLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BookCatalogLayoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BookCatalogLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
