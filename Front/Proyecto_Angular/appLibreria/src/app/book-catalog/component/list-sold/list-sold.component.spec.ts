import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListSoldComponent } from './list-sold.component';

describe('ListSoldComponent', () => {
  let component: ListSoldComponent;
  let fixture: ComponentFixture<ListSoldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListSoldComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListSoldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
