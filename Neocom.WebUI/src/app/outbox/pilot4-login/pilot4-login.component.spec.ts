import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Pilot4LoginComponent } from './pilot4-login.component';

describe('Pilot4LoginComponent', () => {
  let component: Pilot4LoginComponent;
  let fixture: ComponentFixture<Pilot4LoginComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Pilot4LoginComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Pilot4LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
