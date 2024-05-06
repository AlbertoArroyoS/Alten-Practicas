import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAuthorComponent } from './list-author.component';

describe('ListAuthorComponent', () => {
  let component: ListAuthorComponent;
  let fixture: ComponentFixture<ListAuthorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ListAuthorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
