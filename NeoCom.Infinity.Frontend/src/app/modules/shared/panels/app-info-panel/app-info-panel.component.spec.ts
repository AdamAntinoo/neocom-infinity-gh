// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { AppStoreService } from '@app/services/appstore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';

import { AppInfoPanelComponent } from '@app/modules/shared/panels/app-info-panel/app-info-panel.component';

describe('PANEL AppInfoPanelComponent [Module: SHARED]', () => {
    let component: AppInfoPanelComponent;
    let fixture: ComponentFixture<AppInfoPanelComponent>;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            // imports: [
            //     RouterTestingModule.withRoutes(routes)
            // ],
            declarations: [
                // RouteMockUpComponent,
                // DashboardHomePageComponent,
                AppInfoPanelComponent,
                // ServerInfoPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                // { provide: ActivatedRoute, useValue: { queryParams: params } },
                // { provide: AppStoreService, useClass: SupportAppStoreService },
                // { provide: BackendService, useClass: SupportBackendService }
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(AppInfoPanelComponent);
        component = fixture.componentInstance;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
