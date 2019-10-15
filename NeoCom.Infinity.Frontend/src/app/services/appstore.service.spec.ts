// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
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
import { AppStoreService } from './appstore.service';

xdescribe('SERVICE AppStoreService [Module: CORE]', () => {
    let service: AppStoreService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes)
            ],
            declarations: [
                RouteMockUpComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService }
            ]
        })
            .compileComponents();
        service = TestBed.get(AppStoreService);
     });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[core/AppStoreService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });
    
    // - C O D E   C O V E R A G E   P H A S E
    // describe('Code Coverage Phase [JWTDecode2AccountName]', /*fakeAsync(*/() => {
    //     it('JWTDecode2AccountName: get the account name from the token', () => {
    //         service.clearJwtToken();
    //         expect(service.isExpiredToken()).toBeTruthy();
    //     });
    //     it('isExpiredToken.false: jwt token expiration time has not completed', () => {
    //         service.storeJwtToken("-TEST-JWTTOKEN-");
    //         expect(service.isExpiredToken()).toBeFalsy();
    //     });
    //     it('isExpiredToken.true: jwt token expiration time has elapsed', () => {
    //         service.storeJwtToken("-TEST-JWTTOKEN-");
    //         service.timestampJwtToken(-3700 * 1000);
    //         expect(service.isExpiredToken()).toBeTruthy();
    //     });
    // });
});
