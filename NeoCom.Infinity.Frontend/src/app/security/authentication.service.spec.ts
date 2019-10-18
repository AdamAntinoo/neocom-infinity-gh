// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
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
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { AuthenticationService } from './authentication.service';

describe('SERVICE AuthenticationService [Module: CORE]', () => {
   const JWT_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTgzODQ3MjYsImFjY291bnROYW1lIjoiQmV0aCBSaXBsZXkiLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIyMjM2NDciLCJwaWxvdElkIjo5MjIyMzY0N30.Qom8COyZB2sW3bCGm9pnGcIOqw-E2yKDsmGklQW6r9Fhu8jJpkNUv5TUhU2cJjIg5jX3082bZ6eKtRZ3z10vGw";
   // let component: AuthenticationService;
   // let fixture: ComponentFixture<AuthenticationService>;

   beforeEach(() => {
      TestBed.configureTestingModule({
         // schemas: [NO_ERRORS_SCHEMA],
         // imports: [
         //     RouterTestingModule.withRoutes(routes)
         // ],
         // declarations: [
         //     RouteMockUpComponent,
         //     AuthenticationService,
         // ],
         providers: [
            { provide: IsolationService, useClass: SupportIsolationService }
         ]
      })
         .compileComponents();
      // fixture = TestBed.createComponent(AuthenticationService);
      // component = fixture.componentInstance;
   });
   // - C O N S T R U C T I O N   P H A S E
   describe('Construction Phase', () => {
      it('Should be created', () => {
         console.log('><[core/AuthenticationService]> should be created');
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         expect(service).toBeTruthy('component has not been created.');
      });
   });

   // - C O D E   C O V E R A G E   P H A S E
   describe('Code Coverage Phase [isExpiredToken]', /*fakeAsync(*/() => {
      it('isExpiredToken.true: jwt token expiration time does not exist', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         service.clearJwtToken();
         expect(service.isExpiredToken()).toBeTruthy();
      });
      it('isExpiredToken.false: jwt token expiration time has not completed', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         service.storeJwtToken("-TEST-JWTTOKEN-");
         expect(service.isExpiredToken()).toBeFalsy();
      });
      it('isExpiredToken.true: jwt token expiration time has elapsed', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         service.storeJwtToken("-TEST-JWTTOKEN-");
         service.timestampJwtToken(-3700 * 1000);
         expect(service.isExpiredToken()).toBeTruthy();
      });
   });
   describe('Code Coverage Phase [isLoggedIn]', /*fakeAsync(*/() => {
      it('isLoggedIn.false: there is no token', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         service.clearJwtToken();
         expect(service.isLoggedIn()).toBeFalsy();
      });
      it('isLoggedIn.false: there is a jwt token but it is expired', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         service.storeJwtToken("-TEST-JWTTOKEN-");
         service.timestampJwtToken(-3700 * 1000);
         expect(service.isLoggedIn()).toBeFalsy();
      });
      it('isLoggedIn.true: the user is logged in', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         service.storeJwtToken("-TEST-JWTTOKEN-");
         expect(service.isLoggedIn()).toBeTruthy();
      });
   });
   describe('Code Coverage Phase [JWTDecode2AccountName]', () => {
      it('JWTDecode2AccountName: decode the account name', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         const obtained = service.JWTDecode2AccountName(JWT_TOKEN);
         expect(obtained).toBe('Beth Ripley');
      });
   });
   describe('Code Coverage Phase [JWTDecode2UniqueId]', () => {
      it('JWTDecode2UniqueId: decode the unique identifier name', () => {
         const service: AuthenticationService = TestBed.get(AuthenticationService);
         const obtained = service.JWTDecode2UniqueId(JWT_TOKEN);
         expect(obtained).toBe('tranquility/92223647');
      });
   });
});
