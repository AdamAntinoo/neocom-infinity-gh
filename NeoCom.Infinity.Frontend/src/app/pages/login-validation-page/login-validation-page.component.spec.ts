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

import { LoginValidationPageComponent } from './login-validation-page.component';
import { AppInfoPanelComponent } from '@app/panels/app-info-panel/app-info-panel.component';
import { LoginValidationProgressComponent } from '@app/panels/login-validation-progress/login-validation-progress.component';
import { LoginValidationExceptionComponent } from '@app/panels/login-validation-exception/login-validation-exception.component';

describe('PAGE LoginValidationPageComponent [Module: CORE]', () => {
    let component: LoginValidationPageComponent;
    let fixture: ComponentFixture<LoginValidationPageComponent>;
    let params: Subject<Params> = new Subject<Params>();

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
                LoginValidationPageComponent,
                AppInfoPanelComponent,
                LoginValidationProgressComponent,
                LoginValidationExceptionComponent
            ],
            providers: [
                // { provide: IsolationService, useClass: SupportIsolationService },
                { provide: ActivatedRoute, useValue: { queryParams: params } },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService }
            ]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        params = new Subject<Params>();
        fixture = TestBed.createComponent(LoginValidationPageComponent);
        component = fixture.componentInstance;
        // fixture.detectChanges();
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        xit('should be created', () => {
            console.log('><[core/LoginValidationPageComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
        it('should be created with parameters', fakeAsync(() => {
            console.log('><[core/LoginValidationPageComponent]> should be created');
            params.next({
                'code': '-ANY-CODE-',
                'state': 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct'
            });
            tick();
            expect(component).toBeDefined('component has not been created.');
        }));
        xit('Fields should be on initial state', fakeAsync(() => {
            fixture.detectChanges();
            params.next({
                'code': '-ANY-CODE-',
                'state': 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct'
            });
            tick();
            fixture.detectChanges();
            let componentAsAny = component as any;
            // expect(componentAsAny.validationException).toBeUndefined('"validationException" should be <undefined>');
            expect(componentAsAny.paramCode).toBeUndefined('"paramCode" should be <undefined>');
            expect(componentAsAny.paramState).toBeUndefined('"paramState" should be <undefined>');
            expect(componentAsAny.validateAuthorizationTokenSubscription)
                .toBeUndefined('"validateAuthorizationTokenSubscription" should be <undefined>');
        }));
    });
    // - C O D E   C O V E R A G E   P H A S E
    xdescribe('Code Coverage Phase [parameterValidation]', /*fakeAsync(*/() => {
        it('parameterValidation: validate the parameters received on the route', () => {
            let componentAsAny = component as any;
            componentAsAny.paramCode = '-ANY-CODE-';
            componentAsAny.paramState = 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct';
            const validation = componentAsAny.parameterValidation();
            expect(validation).toBeTruthy();
        });
    });
});
