// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - HTTP PACKAGE
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpTestingController } from '@angular/common/http/testing';
// - TESTING
import { async } from '@angular/core/testing';
import { inject } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AppStoreService } from './appstore.service';
import { SupportAppStoreService } from '@app/testing/SupportAppStore.service';
import { HttpClientWrapperService } from './httpclientwrapper.service';

describe('SERVICE HttpClientWrapperService [Module: APP]', () => {
   let service: HttpClientWrapperService;
   // let appStoreService: SupportAppStoreService;

   beforeEach(() => {
      TestBed.configureTestingModule({
         schemas: [NO_ERRORS_SCHEMA],
         imports: [
            RouterTestingModule.withRoutes(routes),
            HttpClientTestingModule
         ],
         declarations: [
            RouteMockUpComponent,
         ],
         providers: [
            { provide: IsolationService, useClass: SupportIsolationService },
            // { provide: AppStoreService, useClass: SupportAppStoreService },
         ]
      })
         .compileComponents();
      service = TestBed.get(HttpClientWrapperService);
      // appStoreService = TestBed.get(AppStoreService);
   });

   // - C O N S T R U C T I O N   P H A S E
   describe('Construction Phase', () => {
      it('Should be created', () => {
         console.log('><[app/HttpClientWrapperService]> should be created');
         expect(service).toBeTruthy('component has not been created.');
      });
   });
});
