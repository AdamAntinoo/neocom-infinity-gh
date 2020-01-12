// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
// import { whenStable } from '@angular/core/testing';
import { flushMicrotasks } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from '@app/services/appstore.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';

import { AppInfoPanelComponent } from '@app/modules/shared/panels/app-info-panel/app-info-panel.component';
import { Alliance } from '@app/domain/Alliance.domain';
import { CorporationPublicDataPanelComponent } from './corporation-public-data-panel.component';
import { Observable } from 'rxjs';
import { Corporation } from '@app/domain/Corporation.domain';
import { Pilot } from '@app/domain/Pilot.domain';

describe('PANEL CorporationPublicDataPanelComponent [Module: SHARED]', () => {
    let component: CorporationPublicDataPanelComponent;
    let fixture: ComponentFixture<CorporationPublicDataPanelComponent>;
    let isolationService: SupportIsolationService;
    let appStoreService: SupportAppStoreService;
    let corporation: Corporation;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                CorporationPublicDataPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
                { provide: BackendService, useClass: SupportBackendService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(CorporationPublicDataPanelComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
        appStoreService = TestBed.get(SupportAppStoreService);
        corporation = new Corporation(appStoreService.directAccessMockResource('corporations'));
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/CorporationPublicDataPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
        it('Fields should be on initial state', () => {
            // console.log('><[shared/CorporationPublicDataPanelComponent]> "_medicalActsSubscription" should be undefined');
            let componentAsAny = component as any;
            expect(componentAsAny.corporationSubscription).toBeUndefined();
            expect(component.corporation).toBeUndefined();
        });
    });

    // - L I F E C Y C L E   P H A S E
    describe('Lifecycle Phase', () => {
        xit('Lifecycle: OnInit -> get the authorized corporation data', async(() => {
            console.log('><[shared/CorporationPublicDataPanelComponent]> Lifecycle: OnInit -> get the authorized corporation data');
            fixture.detectChanges();
            let componentAsAny = component as any;
            expect(componentAsAny.corporationSubscription).toBeUndefined('The initial subscription is undefined.');
            expect(component.corporation).toBeUndefined('The initial corporation is undefined');

            const expected = isolationService.generateRandomString(12);
            let corporation = new Corporation({ name: expected });
            spyOn(appStoreService, 'accessCorporation').and.returnValue(Observable.create((observer) => {
                console.log('><[shared/CorporationPublicDataPanelComponent]> accessCorporation internal observer.');
                observer.next(corporation);
                observer.complete();
            }));
            fixture.whenStable().then(() => {
                fixture.detectChanges();
                expect(componentAsAny.corporationSubscription).toBeDefined('The subscription should exist after the ngOnInit');
                expect(component.corporation).toBeDefined('The initialized corporation should exist.');
                // expect(component.corporation.getName()).toBe(expected);
            });
            component.ngOnInit();
        }));
        xit('Lifecycle: OnDestroy -> close the subscription if active', fakeAsync(() => {
            console.log('><[shared/CorporationPublicDataPanelComponent]> Lifecycle: OnDestroy -> close the subscription if active');
            let componentAsAny = component as any;
            // Check that initial state is the expected.
            // fixture.detectChanges();
            expect(componentAsAny.corporationSubscription).toBeUndefined();
            expect(component.corporation).toBeUndefined();
            // expect(component.ceo).toBeUndefined();
            // expect(component.alliance).toBeUndefined();

            const expected = isolationService.generateRandomString(12);
            let corporation = new Corporation({ name: expected });
            // spyOn(appStoreService, 'accessCorporation').and.returnValue(Observable.of(corporation));
            // spyOn(appStoreService, 'accessCorporation').and.returnValue(Promise.resolve(corporation));
            spyOn(appStoreService, 'accessCorporation').and.returnValue(Observable.create((observer) => {
                observer.next(corporation);
                // observer.complete();
            }));
            component.ngOnInit();
            expect(componentAsAny.corporationSubscription).toBeDefined('The subscription should exist after the ngOnInit');
            fixture.detectChanges();
            expect(component.corporation).toBeDefined();
            expect(component.corporation.getName()).toBe(expected);
            // component.ngOnDestroy();
            // expect(componentAsAny.corporationSubscription).toBeUndefined('The subscription should be completed when destroyed.');
        }));
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getCorporationCeo.success: validate the corporation ceo field', () => {
            const expected = new Pilot({
                "lastUpdateTime": "1970-01-01T00:00:00.000Z",
                "pilotId": 977174846,
                "lastKnownLocation": null,
                "locationRoles": [],
                "actions4Pilot": {},
                "credential": null,
                "birthday": "2009-05-16T22:26:00.000Z",
                "description": "<font size=\"12\" color=\"#bfffffff\"></font><font size=\"24\" color=\"#ff00ff00\"><b><u>N</font><font size=\"24\" color=\"#ffff0000\">R</font><font size=\"24\" color=\"#ff00ff00\">DS</b></u><br><br><br><br><br><br></font><font size=\"36\" color=\"#ff007fff\">Life is a beach ..........<br><br>and then we surf!<br><br><br><br>MKAY !!<br><br></font><font size=\"36\" color=\"#ffffa600\"><a href=\"http://cerlestes.de/eve/explained/index.html\">http://cerlestes.de/eve/explained/index.html</a></font><font size=\"36\" color=\"#ff007fff\"> </font>",
                "corporationId": 1427661573,
                "raceId": 1,
                "url4Icon": "http://image.eveonline.com/character/977174846_256.jpg",
                "ancestryId": 31,
                "bloodlineId": 11,
                "race": {
                    "allianceId": 500001,
                    "description": "Founded on the tenets of patriotism and hard work that carried its ancestors through hardships on an inhospitable homeworld, the Caldari State is today a corporate dictatorship, led by rulers who are determined to see it return to the meritocratic ideals of old. Ruthless and efficient in the boardroom as well as on the battlefield, the Caldari are living emblems of strength, persistence, and dignity.",
                    "name": "Caldari",
                    "raceId": 1
                },
                "ancestry": {
                    "bloodlineId": 11,
                    "description": "According to Achur beliefs, those who can reach inwards and sense the interconnection between all things are capable of accessing the universal consciousness, returning from it with novel ideas. For the Achur, inventions are almost regarded more for their spiritual implications than scientific significance. Creativity is thus a cherished attribute amongst the Achur, with inventors held in high esteem.",
                    "iconId": 3025,
                    "id": 31,
                    "name": "Inventors",
                    "shortDescription": "Those sufficiently attuned with the universe to receive its idea-gifts."
                },
                "bloodline": {
                    "bloodlineId": 11,
                    "charisma": 3,
                    "corporationId": 1000014,
                    "description": "Achura has been part of the Caldari State for three centuries, and yet their culture has always remained something of a mystery. Originally from the Saisio System, they are reclusive and introverted, and show little interest in the ephemeral phenomena of the material world. Intensely spiritual, Achura pilots have only recently taken to the stars, driven in large part by a desire to unlock the secrets of the universe.",
                    "intelligence": 8,
                    "memory": 6,
                    "name": "Achura",
                    "perception": 7,
                    "raceId": 1,
                    "shipTypeId": 601,
                    "willpower": 6
                },
                "gender": "MALE",
                "securityStatus": 3.4609938,
                "name": "Sergio Rodriguez",
                "jsonClass": "Pilot"
            });
            const obtainedEmpty = component.getCorporationCeo();
            expect(obtainedEmpty).toBe(null);
            component.corporation = corporation;
            const obtained = component.getCorporationCeo();
            expect(obtained).toEqual(expected);
        });
        it('getCorporationId.success: validate the corporationId field', () => {
            const expected = isolationService.generateRandomNum(100000, 1000000);
            const obtainedEmpty = component.getCorporationId();
            expect(obtainedEmpty).toBe(0);
            corporation.corporationId = expected;
            component.corporation = corporation;
            const obtained = component.getCorporationId();
            expect(obtained).toBe(expected);
        });
        it('getCorporationName.success: validate the corporation name field', () => {
            const expected = isolationService.generateRandomString(12);
            corporation.corporationPublicData.name = expected;
            component.corporation = corporation;
            const obtained = component.getCorporationName();
            expect(obtained).toBe(expected);
        });
        it('getAlliance: validate the alliance field', () => {
            // Create the data to be tested.
            const expected = isolationService.generateRandomString(12);
            corporation.alliance = new Alliance({ name: expected })
            component.corporation = corporation;
            // component.alliance = corporation.alliance;
            const obtained = component.getAlliance();
            console.log('-[shared/CorporationPublicDataPanelComponent]> alliance: ' + JSON.stringify(obtained));
            expect(obtained).toBeDefined();
            expect(obtained.getName()).toBe(expected);
        });
    });
});
