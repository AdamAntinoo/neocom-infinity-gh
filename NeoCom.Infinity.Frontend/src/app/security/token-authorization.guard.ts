// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - GUARD
import { CanActivate } from '@angular/router';
import { CanActivateChild } from '@angular/router';
import { CanLoad } from '@angular/router';
import { Route, UrlSegment, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';

@Injectable({
   providedIn: 'root'
})
export class TokenAuthorizationGuard implements CanActivate, CanActivateChild, CanLoad {
   canActivate(
      next: ActivatedRouteSnapshot,
      state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      console.log('-[TokenAuthorizationGuard.canActivate] true');
      return true;
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
