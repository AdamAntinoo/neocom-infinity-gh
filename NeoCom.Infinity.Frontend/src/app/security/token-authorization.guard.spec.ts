// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { Subject } from 'rxjs';
// - TESTING
import { async } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { inject } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { TokenAuthorizationGuard } from './token-authorization.guard';

fdescribe('SERVICE AuthenticationService [Module: CORE]', () => {
   beforeEach(() => {
      TestBed.configureTestingModule({
         providers: [TokenAuthorizationGuard]
      })
         .compileComponents();
   });
   // - G U A R D   O P E R A T I O N
   describe('GUARD TokenAuthorizationGuard', () => {
      it('TokenAuthorizationGuard.success', inject([TokenAuthorizationGuard], (guard: TokenAuthorizationGuard) => {
         console.log('><[security/TokenAuthorizationGuard.success]> The guard should allow access');
         expect(guard).toBeTruthy('The guard should allow access.');
      }));
   });
});
