import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginValidationProgressComponent } from './login-validation-progress.component';

describe('LoginValidationProgressComponent', () => {
  let component: LoginValidationProgressComponent;
  let fixture: ComponentFixture<LoginValidationProgressComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginValidationProgressComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginValidationProgressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
