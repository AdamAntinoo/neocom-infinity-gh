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
import { BackendService } from './backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { Corporation } from '@app/domain/Corporation.domain';
import { Pilot } from '@app/domain/Pilot.domain';
import { Credential } from '@app/domain/Credential.domain';
import { environment } from '@env/environment';

describe('SERVICE AppStoreService [Module: CORE]', () => {
    let service: AppStoreService;
    let isolationService: SupportIsolationService;

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
                { provide: BackendService, useClass: SupportBackendService }
            ]
        })
            .compileComponents();
        service = TestBed.get(AppStoreService);
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[core/AppStoreService]> should be created');
            expect(service).toBeTruthy('service has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [Store Data Downloaders]', () => {
        it('downloadCorporation.success: download and transform the Corporation data', () => {
            const corporationId = isolationService.generateRandomNum(100000, 1000000);
            let serviceAsAny = service as any;
            serviceAsAny.downloadCorporation(corporationId)
                .subscribe((corporation: Corporation) => {
                    expect(corporation).toBeDefined();
                    expect(corporation.getName()).toBe('BnFqbOtzrQKR');
                })
        });
        it('downloadPilot.success: download and transform the Corporation data', () => {
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
            const obtained: Credential = service.accessCredential();
            expect(obtained).toBeDefined();
            expect(obtained.getJsonClass()).toBe('Credential');
            expect(obtained.getAccountName()).toBe('Beth Ripley');
        });
    });
    describe('Code Coverage Phase [Global Store]', () => {
        it('getCorporationIdentifier.success: get the session stored credential', () => {
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
    });
    describe('Code Coverage Phase [Global Store]', () => {
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
    });
});
