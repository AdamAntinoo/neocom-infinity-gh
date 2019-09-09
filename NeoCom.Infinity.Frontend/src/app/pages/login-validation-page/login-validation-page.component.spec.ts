import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginValidationPageComponent } from './login-validation-page.component';

describe('LoginValidationPageComponent', () => {
  let component: LoginValidationPageComponent;
  let fixture: ComponentFixture<LoginValidationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginValidationPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginValidationPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
