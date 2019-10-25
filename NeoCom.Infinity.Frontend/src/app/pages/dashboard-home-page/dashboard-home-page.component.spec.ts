// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { Params } from '@angular/router';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { AppStoreService } from '@app/services/appstore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';

// import { LoginValidationPageComponent } from './login-validation-page.component';
// import { AppInfoPanelComponent } from '@app/panels/app-info-panel/app-info-panel.component';
import { LoginValidationProgressComponent } from '@app/panels/login-validation-progress/login-validation-progress.component';
import { LoginValidationExceptionComponent } from '@app/panels/login-validation-exception/login-validation-exception.component';
import { AppInfoPanelComponent } from '@app/modules/shared/panels/app-info-panel/app-info-panel.component';
import { ServerInfoPanelComponent } from '@app/modules/header/server-info-panel/server-info-panel.component';
import { DashboardHomePageComponent } from './dashboard-home-page.component';

describe('PAGE DashboardHomePageComponent [Module: CORE]', () => {
    let component: DashboardHomePageComponent;
    let fixture: ComponentFixture<DashboardHomePageComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
                DashboardHomePageComponent,
                AppInfoPanelComponent,
                ServerInfoPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                // { provide: ActivatedRoute, useValue: { queryParams: params } },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(DashboardHomePageComponent);
        component = fixture.componentInstance;
    });
    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
