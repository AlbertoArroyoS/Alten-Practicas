import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfPersonComponent } from './inf-person.component';

describe('InfPersonComponent', () => {
  let component: InfPersonComponent;
  let fixture: ComponentFixture<InfPersonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InfPersonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
