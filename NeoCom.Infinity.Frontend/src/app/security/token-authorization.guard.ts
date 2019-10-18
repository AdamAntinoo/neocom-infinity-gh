// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - GUARD
import { CanActivate } from '@angular/router';
import { CanActivateChild } from '@angular/router';
import { CanLoad } from '@angular/router';
import { Route } from '@angular/router';
import { UrlSegment } from '@angular/router';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
// - SERVICES
import { AuthenticationService } from './authentication.service';

@Injectable({
   providedIn: 'root'
})
export class TokenAuthorizationGuard implements CanActivate, CanActivateChild, CanLoad {
   constructor(protected authenticationService: AuthenticationService) { }
   canActivate(
      next: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      const pass: boolean = this.authenticationService.isLoggedIn(); // Check the token exists and it is not expired.
      console.log('-[TokenAuthorizationGuard.canActivate] pass: ' + pass);
      return pass;
   }
   canActivateChild(
      next: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      console.log('-[TokenAuthorizationGuard.canActivateChild] true');
      return true;
   }
   canLoad(
      route: Route,
      segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
      console.log('-[TokenAuthorizationGuard.canLoad] true');
      return true;
   }
}
