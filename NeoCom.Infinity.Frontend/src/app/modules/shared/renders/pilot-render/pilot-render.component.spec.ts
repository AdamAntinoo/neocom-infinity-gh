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
import { PilotRenderComponent } from './pilot-render.component';
import { Pilot } from '@app/domain/Pilot.domain';

describe('RENDER PilotRenderComponent [Module: SHARED]', () => {
    let component: PilotRenderComponent;
    let fixture: ComponentFixture<PilotRenderComponent>;
    let isolationService: SupportIsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                PilotRenderComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                // { provide: BackendService, useClass: SupportBackendService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(PilotRenderComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/PilotRenderComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
        it('Fields should be on initial state', () => {
            console.log('><[shared/PilotRenderComponent]> Fields should be on initial state');
            let componentAsAny = component as any;
            expect(componentAsAny.node).toBeUndefined();
        });
    });

    // - I N P U T / O U T P U T   P H A S E
    describe('Input/Output Phase', () => {
        it('node (Input): validate node reception', () => {
            console.log('><[shared/PilotRenderComponent]> node (Input): validate node reception');
            // Check the initial state to undefined.
            expect(component.node).toBeUndefined();

            // Create the test node to be used on the render.
            console.log('><[shared/PilotRenderComponent]> Input: node');
            component.node = new Pilot();
            expect(component.node).toBeDefined();
        });
    });
});
