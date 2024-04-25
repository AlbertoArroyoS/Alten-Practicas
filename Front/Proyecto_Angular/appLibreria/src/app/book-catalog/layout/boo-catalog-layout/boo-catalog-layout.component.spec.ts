import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BooCatalogLayoutComponent } from './boo-catalog-layout.component';

describe('BooCatalogLayoutComponent', () => {
  let component: BooCatalogLayoutComponent;
  let fixture: ComponentFixture<BooCatalogLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BooCatalogLayoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BooCatalogLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
