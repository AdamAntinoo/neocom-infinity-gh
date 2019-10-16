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
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { environment } from '@env/environment';

import { ServerInfoPanelComponent } from './server-info-panel.component';
import { ServerInfo } from '@app/domain/ServerInfo.domain';

describe('PANEL ServerInfoPanelComponent [Module: SHARED]', () => {
    let component: ServerInfoPanelComponent;
    let fixture: ComponentFixture<ServerInfoPanelComponent>;
    let isolationService: SupportIsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
               ServerInfoPanelComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(ServerInfoPanelComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/ServerInfoPanelComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
    });

    // - I N I T I A L I Z A T I O N   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getServerName: validate the name field when not initialized', () => {
            const expected = '-PENDING-UPDATE-';
            const obtained = component.getServerName();
            expect(obtained).toBe(expected)
        });
        it('getServerCapsuleers: validate the name field when not initialized', () => {
            const expected = -1;
            const obtained = component.getServerCapsuleers();
            expect(obtained).toBe(expected)
        });
        it('getLastStartTime: validate the last start field not initialized', () => {
            const expected = isolationService.generateRandomString(12);
            const obtained = component.getLastStartTime();
            expect(obtained).toBeDefined()
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getServerName: validate the name field', () => {
            const expected = isolationService.generateRandomString(12);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerInfo({ name: expected });
            const obtained = component.getServerName();
            expect(obtained).toBe(expected)
        });
        it('getServerCapsuleers: validate the players field', () => {
            const expected = isolationService.generateRandomNum(100, 100000);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerInfo({ players: expected });
            const obtained = component.getServerCapsuleers();
            expect(obtained).toBe(expected)
        });
        xit('getLastStartTime: validate the last start field', () => {
            const expected = isolationService.generateRandomString(12);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerInfo({ start_time: expected });
            const obtained = component.getLastStartTime();
            expect(obtained).toBe(expected)
        });
    });
    describe('Code Coverage Phase [setters]', () => {
        it('setServerName: validate the name field', () => {
            const expected = isolationService.generateRandomString(12);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerInfo();
            componentAsAny.serverInfo.setServerName(expected);
            const obtained = component.getServerName();
            expect(obtained).toBe(expected)
        });
    });
});
