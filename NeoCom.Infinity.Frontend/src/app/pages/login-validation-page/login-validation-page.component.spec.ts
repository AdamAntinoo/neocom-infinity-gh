import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginValidationPageComponent } from './login-validation-page.component';
import { AppInfoPanelComponent } from '@app/panels/app-info-panel/app-info-panel.component';
import { LoginValidationProgressComponent } from '@app/panels/login-validation-progress/login-validation-progress.component';
import { LoginValidationExceptionComponent } from '@app/panels/login-validation-exception/login-validation-exception.component';

describe('LoginValidationPageComponent', () => {
  let component: LoginValidationPageComponent;
  let fixture: ComponentFixture<LoginValidationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginValidationPageComponent, AppInfoPanelComponent ,LoginValidationProgressComponent,
        LoginValidationExceptionComponent]
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
