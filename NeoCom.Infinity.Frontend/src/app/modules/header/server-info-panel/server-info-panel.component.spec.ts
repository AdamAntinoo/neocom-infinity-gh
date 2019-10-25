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
import { ServerStatus } from '@app/domain/ServerStatus.domain';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';

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
                { provide: BackendService, useClass: SupportBackendService },
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

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('getServerName.success: validate the name field', () => {
            const expected = isolationService.generateRandomString(12);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerStatus({ server: expected });
            const obtained = component.getServerName();
            expect(obtained).toBe(expected)
        });
        it('getServerName.failure: validate the name field', () => {
            const obtained = component.getServerName();
            expect(obtained).toBe('-')
        });
        it('getServerStatus.online: validate the server status field', () => {
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerStatus();
            const obtained = component.getServerStatus();
            expect(obtained).toBe('ONLINE')
        });
        it('getServerStatus.offline: validate the server status field', () => {
            const obtained = component.getServerStatus();
            expect(obtained).toBe('OFFLINE')
        });
        it('getServerCapsuleers: validate the players field', () => {
            const expected = isolationService.generateRandomNum(100, 100000);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerStatus({ players: expected });
            const obtained = component.getServerCapsuleers();
            expect(obtained).toBe(expected)
        });
        it('getLastStartTime.success: validate the last start field', () => {
            const expected = isolationService.generateRandomString(12);
            let componentAsAny = component as any;
            componentAsAny.serverInfo = new ServerStatus({ start_time: expected });
            const obtained = component.getLastStartTime();
            expect(obtained).toBe(expected)
        });
        it('getLastStartTime.failure: validate the last start field', () => {
            const obtained = component.getLastStartTime();
            expect(obtained).toBeDefined();
        });
    });
});
