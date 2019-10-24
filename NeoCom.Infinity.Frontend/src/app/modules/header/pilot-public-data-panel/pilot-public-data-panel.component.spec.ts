// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { flushMicrotasks } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from '@app/services/appstore.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';

import { AppInfoPanelComponent } from '@app/modules/shared/panels/app-info-panel/app-info-panel.component';
import { Alliance } from '@app/domain/Alliance.domain';
import { Observable } from 'rxjs';
import { Corporation } from '@app/domain/Corporation.domain';
import { environment } from '@env/environment';
import { PilotPublicDataPanelComponent } from './pilot-public-data-panel.component';
import { Pilot } from '@app/domain/Pilot.domain';

describe('PANEL PilotPublicDataPanelComponent [Module: SHARED]', () => {
    let component: PilotPublicDataPanelComponent;
    let fixture: ComponentFixture<PilotPublicDataPanelComponent>;
    let isolationService: SupportIsolationService;
    // let appStoreService: SupportAppStoreService;
    const pilot = new Pilot(
        {
            "pilotId": 92223647,
            "corporationId": 98384726,
            "url4Icon": "http://image.eveonline.com/character/92223647_256.jpg",
            "ancestryId": 22,
            "ancestry": {
                "bloodline_id": 4,
                "description": "Many Brutors have a modest background, and are often found performing backbreaking labor in the farms, mines and factories of the Republic — and before that, toiling under the yoke of the Amarr Empire. They still take great pride in their jobs, excelling as craftsmen and builders.",
                "icon_id": 1662,
                "id": 22,
                "name": "Workers",
                "short_description": "People of the fields, mines and factories."
            },
            "birthday": "2012-07-05T21:53:15.000Z",
            "bloodlineId": 4,
            "bloodline": {
                "bloodline_id": 4,
                "charisma": 6,
                "corporation_id": 1000049,
                "description": "A martial, strong-willed people, the Brutor hold their tribal heritage close to their hearts, and are renowned for living regimented, disciplined lives. Despite presenting a tough, no-nonsense exterior, they are deeply introspective, aware of even the smallest detail at all times. Immersed in ancient martial traditions that begin at childhood, they are physically robust individuals and intimidating to face in the flesh.",
                "intelligence": 4,
                "memory": 4,
                "name": "Brutor",
                "perception": 9,
                "race_id": 2,
                "ship_type_id": 588,
                "willpower": 7
            },
            "description": "",
            "gender": "FEMALE",
            "name": "Beth Ripley",
            "raceId": 2,
            "race": {
                "alliance_id": 500002,
                "description": "Once a thriving tribal civilization, the Minmatar were enslaved by the Amarr Empire for more than 700 years until a massive rebellion freed most, but not all, of those held in servitude. The Minmatar people today are resilient, ingenious, and hard-working. Many of them believe that democracy, though it has served them well for a long time, can never restore what was taken from them so long ago. For this reason they have formed a government truly reflective of their tribal roots. They will forever resent the Amarrians, and yearn for the days before the Empire's accursed ships ever reached their home skies.",
                "name": "Minmatar",
                "race_id": 2
            },
            "securityStatus": 0
        });

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                PilotPublicDataPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                { provide: AppStoreService, useClass: SupportAppStoreService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(PilotPublicDataPanelComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/PilotPublicDataPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
        it('Fields should be on initial state', () => {
            // console.log('><[shared/PilotPublicDataPanelComponent]> "_medicalActsSubscription" should be undefined');
            let componentAsAny = component as any;
            expect(componentAsAny.pilotSubscription).toBeUndefined();
            expect(component.pilot).toBeUndefined();
        });
    });

    // - L I F E C Y C L E   P H A S E
    describe('Lifecycle Phase', () => {
        xit('Lifecycle: OnInit -> get the authorized corporation data', fakeAsync(() => {
            console.log('><[shared/PilotPublicDataPanelComponent]> Lifecycle: OnInit -> get the authorized corporation data');
            let componentAsAny = component as any;
            expect(componentAsAny.pilotSubscription).toBeUndefined();
            expect(component.pilot).toBeUndefined();
        }));
    });
});
