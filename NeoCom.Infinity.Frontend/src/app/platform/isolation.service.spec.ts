// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
// - WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
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
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// import { IsolationService } from './authentication.service';

xdescribe('SERVICE AuthenticationService [Module: CORE]', () => {
    // let component: AuthenticationService;
    // let fixture: ComponentFixture<AuthenticationService>;
    let service: IsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
                //     AuthenticationService,
            ],
            providers: [
                { provide: WebStorageService, useClass: WebStorageService }
            ]
        })
            .compileComponents();
        service = TestBed.get(IsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[core/AuthenticationService]> should be created');
            // const service: IsolationService = TestBed.get(IsolationService);
            expect(service).toBeTruthy('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    // describe('Code Coverage Phase [isExpiredToken]', /*fakeAsync(*/() => {
    //     it('isExpiredToken.true: jwt token expiration time does not exist', () => {
    //         const service: AuthenticationService = TestBed.get(AuthenticationService);
    //         service.clearJwtToken();
    //         expect(service.isExpiredToken()).toBeTruthy();
    //     });
    //     it('isExpiredToken.false: jwt token expiration time has not completed', () => {
    //         const service: AuthenticationService = TestBed.get(AuthenticationService);
    //         service.storeJwtToken("-TEST-JWTTOKEN-");
    //         expect(service.isExpiredToken()).toBeFalsy();
    //     });
    //     it('isExpiredToken.true: jwt token expiration time has elapsed', () => {
    //         const service: AuthenticationService = TestBed.get(AuthenticationService);
    //         service.storeJwtToken("-TEST-JWTTOKEN-");
    //         service.timestampJwtToken(-3700 * 1000);
    //         expect(service.isExpiredToken()).toBeTruthy();
    //     });
    // });
});
