import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CorePublicLayoutComponent } from './core-public-layout.component';

describe('CorePublicLayoutComponent', () => {
  let component: CorePublicLayoutComponent;
  let fixture: ComponentFixture<CorePublicLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CorePublicLayoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CorePublicLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
