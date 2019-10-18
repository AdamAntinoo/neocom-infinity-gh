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

// import { AllianceRenderComponent } from './server-info-panel.component';
import { ServerInfo } from '@app/domain/ServerInfo.domain';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { BackendService } from '@app/services/backend.service';
import { SupportBackendService } from '@app/testing/SupportBackend.service';
import { AllianceRenderComponent } from './alliance-render.component';

describe('RENDER AllianceRenderComponent [Module: SHARED]', () => {
   let component: AllianceRenderComponent;
   let fixture: ComponentFixture<AllianceRenderComponent>;
   let isolationService: SupportIsolationService;

   beforeEach(() => {
      TestBed.configureTestingModule({
         schemas: [NO_ERRORS_SCHEMA],
         declarations: [
            AllianceRenderComponent,
         ],
         providers: [
            { provide: IsolationService, useClass: SupportIsolationService },
            // { provide: BackendService, useClass: SupportBackendService },
         ]
      })
         .compileComponents();
      fixture = TestBed.createComponent(AllianceRenderComponent);
      component = fixture.componentInstance;
      isolationService = TestBed.get(IsolationService);
   });

   // - C O N S T R U C T I O N   P H A S E
   describe('Construction Phase', () => {
      it('Should be created', () => {
         console.log('><[shared/AllianceRenderComponent]> should be created');
         expect(component).toBeDefined('component has not been created.');
      });
   });
});
