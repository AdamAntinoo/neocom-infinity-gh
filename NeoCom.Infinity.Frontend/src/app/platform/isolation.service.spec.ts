// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
// - WEBSTORAGE
import { LOCAL_STORAGE, InMemoryStorageService } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
import { SupportWebStorageService } from '@app/testing/SupportWebStorageService';
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
import { ToastrManager } from 'ng6-toastr-notifications';
import { Router } from '@angular/router';
// import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// import { IsolationService } from './authentication.service';

xdescribe('SERVICE IsolationService [Module: APP]', () => {
   let service: IsolationService;

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
            { provide: WebStorageService, useClass: InMemoryStorageService },
            { provide: ToastrManager, useClass: ToastrManager },
            // { provide: Router, useClass: RouterTestingModule }
         ]
      })
         .compileComponents();
      service = TestBed.get(IsolationService);
   });

   // - C O N S T R U C T I O N   P H A S E
   xdescribe('Construction Phase', () => {
      it('Should be created', () => {
         console.log('><[app/AuthenticationService]> should be created');
         // const service: IsolationService = TestBed.get(IsolationService);
         expect(service).toBeTruthy('component has not been created.');
      });
   });

   // - C O D E   C O V E R A G E   P H A S E
   xdescribe('Code Coverage Phase [environment]', () => {
      it('environment.getServerName: check environment getters', () => {
         expect(service.getServerName()).toBe('expected');
      });
      it('environment.getServerName: check environment getters', () => {
         expect(service.getAppName()).toBe('neocom.infinity');
      });
   });
});
