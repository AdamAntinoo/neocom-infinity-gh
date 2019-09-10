import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginValidationPageComponent } from './login-validation-page.component';
import { AppInfoPanelComponent } from '@app/panels/app-info-panel/app-info-panel.component';

describe('LoginValidationPageComponent', () => {
  let component: LoginValidationPageComponent;
  let fixture: ComponentFixture<LoginValidationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginValidationPageComponent, AppInfoPanelComponent ]
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
