import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CorePrivateLayoutComponent } from './core-private-layout.component';

describe('CorePrivateLayoutComponent', () => {
  let component: CorePrivateLayoutComponent;
  let fixture: ComponentFixture<CorePrivateLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CorePrivateLayoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CorePrivateLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
