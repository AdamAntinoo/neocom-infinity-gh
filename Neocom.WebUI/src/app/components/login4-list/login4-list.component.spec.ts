import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Login4ListComponent } from './login4-list.component';

describe('Login4ListComponent', () => {
  let component: Login4ListComponent;
  let fixture: ComponentFixture<Login4ListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Login4ListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Login4ListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
