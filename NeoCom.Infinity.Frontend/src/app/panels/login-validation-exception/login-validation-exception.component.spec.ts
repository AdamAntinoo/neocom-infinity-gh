import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginValidationExceptionComponent } from './login-validation-exception.component';

describe('LoginValidationExceptionComponent', () => {
  let component: LoginValidationExceptionComponent;
  let fixture: ComponentFixture<LoginValidationExceptionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginValidationExceptionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginValidationExceptionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
