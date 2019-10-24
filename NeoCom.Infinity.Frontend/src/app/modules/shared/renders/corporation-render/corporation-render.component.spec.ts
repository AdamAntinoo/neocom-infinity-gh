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

// import { CorporationRenderComponent } from './server-info-panel.component';
import { ServerStatus } from '@app/domain/ServerStatus.domain';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { Alliance } from '@app/domain/Alliance.domain';
import { CorporationRenderComponent } from './corporation-render.component';

fdescribe('RENDER CorporationRenderComponent [Module: SHARED]', () => {
    let component: CorporationRenderComponent;
    let fixture: ComponentFixture<CorporationRenderComponent>;
    let isolationService: SupportIsolationService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            declarations: [
                CorporationRenderComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                // { provide: BackendService, useClass: SupportBackendService },
            ]
        })
            .compileComponents();
        fixture = TestBed.createComponent(CorporationRenderComponent);
        component = fixture.componentInstance;
        isolationService = TestBed.get(IsolationService);
    });

    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[shared/CorporationRenderComponent]> should be created');
            expect(component).toBeDefined('component has not been created.');
        });
    });
});
