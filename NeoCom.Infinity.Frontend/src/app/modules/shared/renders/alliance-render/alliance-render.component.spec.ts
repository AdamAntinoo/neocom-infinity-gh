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
import { Alliance } from '@app/domain/Alliance.domain';

describe('RENDER AllianceRenderComponent [Module: SHARED]', () => {
   let component: AllianceRenderComponent;
   let fixture: ComponentFixture<AllianceRenderComponent>;
   let isolationService: SupportIsolationService;
   const alliance = new Alliance(
      {
         "creator_corporation_id": 98388312,
         "creator_id": 1597785719,
         "date_founded": "2015-04-02T16:29:43Z",
         "executor_corporation_id": 98500220,
         "name": "Pandemic Horde",
         "ticker": "REKTD"
      }
   );

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

   // - C O D E   C O V E R A G E   P H A S E
   describe('Code Coverage Phase [getters]', () => {
      it('getName.success: validate the name field', () => {
         const expected = isolationService.generateRandomString(12);
         alliance.name = expected;
         component.node = alliance;
         const obtained = component.getName();
         expect(obtained).toBe(expected);
      });
      it('getName.failure: validate the name field', () => {
         const obtained = component.getName();
         expect(obtained).toBe('-');
      });
      it('getAllianceIcon.success: validate the alliance icon field', () => {
         const expected = isolationService.generateRandomString(12);
         alliance.url4Icon = expected;
         component.node = alliance;
         const obtained = component.getAllianceIcon();
         expect(obtained).toBe(expected);
      });
      it('getAllianceIcon.failure: validate the alliance icon field', () => {
         const obtained = component.getAllianceIcon();
         expect(obtained).toBe(environment.DEFAULT_AVATAR_PLACEHOLDER);
      });
   });
});
