// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { AppStoreService } from '@app/services/appstore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';

import { LoginValidationPageComponent } from './login-validation-page.component';
import { AppInfoPanelComponent } from '@app/panels/app-info-panel/app-info-panel.component';
import { LoginValidationProgressComponent } from '@app/panels/login-validation-progress/login-validation-progress.component';
import { LoginValidationExceptionComponent } from '@app/panels/login-validation-exception/login-validation-exception.component';

describe('LoginValidationPageComponent', () => {
  let component: LoginValidationPageComponent;
  let fixture: ComponentFixture<LoginValidationPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
        schemas: [NO_ERRORS_SCHEMA],
        imports: [
            RouterTestingModule
        ],
        declarations: [
            LoginValidationPageComponent,
            AppInfoPanelComponent,
            LoginValidationProgressComponent,
            LoginValidationExceptionComponent
        ],
        providers: [
            // { provide: IsolationService, useClass: SupportIsolationService },
            { provide: AppStoreService, useClass: SupportAppStoreService },
            { provide: BackendService, useClass: SupportBackendService }
        ]
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
