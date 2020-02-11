// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
import { Router } from '@angular/router';
// - TESTING
import { inject } from '@angular/core/testing';
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
import { BackendService } from './backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { HttpClientWrapperService } from './httpclientwrapper.service';
import { SupportHttpClientWrapperService } from '@app/testing/SupportHttpClientWrapperService.service';
// - DOMAIN
import { Corporation } from '@app/domain/Corporation.domain';
import { Pilot } from '@app/domain/Pilot.domain';
import { Credential } from '@app/domain/Credential.domain';
import { environment } from '@env/environment';
import { NeoComException } from '@app/platform/NeoComException';
import { ExceptionCatalog } from '@app/platform/ExceptionCatalog';

describe('SERVICE AppStoreService [Module: CORE]', () => {
    let service: AppStoreService;
    let isolationService: SupportIsolationService;
    // let routeChecker: RouteMockUpComponent;

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
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: BackendService, useClass: SupportBackendService },
                { provide: HttpClientWrapperService, useClass: SupportHttpClientWrapperService }
            ]
        })
            .compileComponents();
        service = TestBed.get(AppStoreService);
        isolationService = TestBed.get(IsolationService);
        // routeChecker: TestBed.get(RouteMockUpComponent);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[core/AppStoreService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Routing]', () => {
        it('route2Destination.success: change route to selected destination', inject([Router], (router: Router) => {
            spyOn(router, 'navigate').and.stub();
            service.route2Destination('loginValidation');
            expect(router.navigate).toHaveBeenCalledWith(['loginValidation']);
        }));
    });
    describe('Code Coverage Phase [Store Data Downloaders]', () => {
        it('downloadCorporation.success: download and transform the Corporation data', () => {
            const corporationId = isolationService.generateRandomNum(100000, 1000000);
            const corporationName = isolationService.generateRandomString(12);
            let serviceAsAny = service as any;
            serviceAsAny.downloadCorporation({ corporationId: corporationId, corporationPublicData: { name: corporationName } })
                .subscribe((corporation: Corporation) => {
                    console.log('downloadCorporation.success> corporation: ' + JSON.stringify(corporation));
                    expect(corporation).toBeDefined();
                    // expect(corporation.getName()).toBe('Planet - Express');
                })
        });
        it('downloadPilot.success: download and transform the Pilot data', () => {
            const pilotId = isolationService.generateRandomNum(100000, 1000000);
            let serviceAsAny = service as any;
            serviceAsAny.downloadPilot(pilotId)
                .subscribe((pilot: Pilot) => {
                    expect(pilot).toBeDefined();
                    expect(pilot.name).toBe('Beth Ripley');
                })
        });
    });
    describe('Code Coverage Phase [Global Store]', () => {
        it('accessCredential.success: get the session stored credential', () => {
            const credential = new Credential(
                {
                    "jsonClass": "Credential",
                    "uniqueId": "tranquility/92223647",
                    "accountId": 92223647,
                    "accountName": "Beth Ripley",
                    "corporationId": 98384726,
                    "assetsCount": 1476,
                    "walletBalance": 6.309543632E8,
                    "raceName": "Amarr",
                    "dataSource": "tranquility"
                }
            );
            isolationService.setToSession(environment.CREDENTIAL_KEY, credential);
            let obtained: Credential = service.accessCredential();
            expect(obtained).toBeDefined();
            expect(obtained.getJsonClass()).toBe('Credential');
            expect(obtained.getAccountName()).toBe('Beth Ripley');
            isolationService.setToSession(environment.CREDENTIAL_KEY, null);
            try {
                obtained = service.accessCredential();
            } catch (exception) {
                expect(exception).toBeDefined();
                expect(exception.getUserMessage().toEqual(ExceptionCatalog.AUTHORIZATION_MISSING.getUserMessage()))
            }
        });
        // });
        // describe('Code Coverage Phase [Global Store]', () => {
        it('getCorporationIdentifier.success: get the stored corporation identifier', () => {
            const credential = new Credential(
                {
                    "jsonClass": "Credential",
                    "uniqueId": "tranquility/92223647",
                    "accountId": 92223647,
                    "accountName": "Beth Ripley",
                    "corporationId": 98384726,
                    "assetsCount": 1476,
                    "walletBalance": 6.309543632E8,
                    "raceName": "Amarr",
                    "dataSource": "tranquility"
                }
            );
            isolationService.setToSession(environment.CREDENTIAL_KEY, credential);
            const obtained: number = service.getCorporationIdentifier();
            expect(obtained).toBe(98384726);
        });
        // });
        // describe('Code Coverage Phase [Global Store]', () => {
        it('getPilotIdentifier.success: get the session stored credential', () => {
            const credential = new Credential(
                {
                    "jsonClass": "Credential",
                    "uniqueId": "tranquility/92223647",
                    "accountId": 92223647,
                    "accountName": "Beth Ripley",
                    "corporationId": 98384726,
                    "assetsCount": 1476,
                    "walletBalance": 6.309543632E8,
                    "raceName": "Amarr",
                    "dataSource": "tranquility"
                }
            );
            isolationService.setToSession(environment.CREDENTIAL_KEY, credential);
            const obtained: number = service.getPilotIdentifier();
            expect(obtained).toBe(92223647);
        });
        it('getLastInterceptedException.success: get the last recorded exception', () => {
            const name = isolationService.generateRandomString(12);
            const exception = new NeoComException(
                {
                    "message": name
                }
            );
            // const obtained: number = service.getPilotIdentifier();
            expect(service.getLastInterceptedException()).toBeUndefined();
            service.setLastInterceptedException(exception);
            expect(service.getLastInterceptedException()).toBeDefined();
            expect(service.getLastInterceptedException().getUserMessage()).toEqual(name);
        });
    });
    xdescribe('Code Coverage Phase [Store Access Section]', () => {
        it('clearStore.success: clean any stored values', () => {
            console.log('><[Store Access Section]> clearStore.success: clean any stored values');
            // Force the load of some value.
            // service.accessCorporation();
        });
    });
    describe('Code Coverage Phase [Global Support Methods]', () => {
        it('isEmpty.success: check if a variable has contents', () => {
            console.log('><[Global Support Methods]> isEmpty.success: check if a variable has contents');
            expect(service.isEmpty()).toBeTruthy();
            expect(service.isEmpty(null)).toBeTruthy();
            expect(service.isEmpty('')).toBeTruthy();
            expect(service.isEmpty("")).toBeTruthy();
            expect(service.isEmpty([])).toBeTruthy();
            expect(service.isEmpty(" ")).toBeFalsy();
        });
        it('accessProperties.success: access a properties file', () => {
            console.log('><[Global Support Methods]> accessProperties.success: access a properties file');
            expect(service.accessProperties('tab-definitions')).toBeDefined();
            // expect(service.accessProperties('undefined')).toBeUndefined();
        });
    });
    describe('Code Coverage Phase [JWT Decode]', () => {
        it('JWTDecode2AccountName.success: decode the account name from the token', () => {
            console.log('><[JWT Decode]> JWTDecode2AccountName.success: decode the account name from the token');
            const token: string = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6MTQyNzY2MTU3MywiYWNjb3VudE5hbWUiOiJBZGFtIEFudGlub28iLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIwMDIwNjciLCJwaWxvdElkIjo5MjAwMjA2N30.6JgBvtHyhvD8aY8-I4075tb433mYMpn9sNeYCkIO28LbhqVR4CZ-x1t_sk4IOLLtzSN07bF4c7ZceWw_ta4Brw';
            expect(service.JWTDecode2AccountName(token)).toEqual('Adam Antinoo');
        });
        it('JWTDecode2UniqueId.success: decode the account name from the token', () => {
            console.log('><[JWT Decode]> JWTDecode2UniqueId.success: decode the unique id from the token');
            const token: string = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6MTQyNzY2MTU3MywiYWNjb3VudE5hbWUiOiJBZGFtIEFudGlub28iLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIwMDIwNjciLCJwaWxvdElkIjo5MjAwMjA2N30.6JgBvtHyhvD8aY8-I4075tb433mYMpn9sNeYCkIO28LbhqVR4CZ-x1t_sk4IOLLtzSN07bF4c7ZceWw_ta4Brw';
            expect(service.JWTDecode2UniqueId(token)).toEqual('tranquility/92002067');
        });
    });
    describe('Code Coverage Phase [Notifications]', () => {
        it('successNotification.success: check notification calls', () => {
            console.log('><[Notifications]> successNotification.success: check notification calls');
            let spy = spyOn(isolationService, 'successNotification');
            const message = isolationService.generateRandomString(12);
            service.successNotification(message);
            expect(spy).toHaveBeenCalled();
        });
        it('errorNotification.success: check notification calls', () => {
            console.log('><[Notifications]> errorNotification.success: check notification calls');
            let spy = spyOn(isolationService, 'errorNotification');
            const message = isolationService.generateRandomString(12);
            service.errorNotification(message);
            expect(spy).toHaveBeenCalled();
        });
        it('warningNotification.success: check notification calls', () => {
            console.log('><[Notifications]> warningNotification.success: check notification calls');
            let spy = spyOn(isolationService, 'warningNotification');
            const message = isolationService.generateRandomString(12);
            service.warningNotification(message);
            expect(spy).toHaveBeenCalled();
        });
        it('infoNotification.success: check notification calls', () => {
            console.log('><[Notifications]> infoNotification.success: check notification calls');
            let spy = spyOn(isolationService, 'infoNotification');
            const message = isolationService.generateRandomString(12);
            service.infoNotification(message);
            expect(spy).toHaveBeenCalled();
        });
    });
});
