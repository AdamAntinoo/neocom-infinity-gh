// - CORE
import { Injectable } from '@angular/core';
// - ENVIRONMENT
import { environment } from '@env/environment';
// - WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
import * as jwt_decode from 'jwt-decode';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { GlobalService } from '@app/platform/global.service';
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Credential } from '../domain/Credential.domain';

@Injectable({
    providedIn: 'root'
})
export class SupportAppStoreService /*extends GlobalService*/ {
    private credential: Credential;

    constructor(
        // protected isolation: IsolationService,
        // protected router: Router,
        protected backendService: BackendService) {
        // super(isolation);
    }

    // - G L O B A L   S T O R E
    public setCredential(newCredential: Credential): void {
        this.credential = newCredential;
    }

    // - E N V I R O N M E N T    C A L L S
    public getApplicationName(): string {
        return environment.appName;
    }
    public getApplicationVersion(): string {
        return environment.appVersion;
    }

    // - G L O B A L   A C C E S S   M E T H O D S
    public isNonEmptyString(str: string): boolean {
        return str && str.length > 0; // Or any other logic, removing whitespace, etc.
    }
    public isNonEmptyArray(data: any[]): boolean {
        if (null == data) return false;
        if (data.length < 1) return false;
        return true;
    }
    public isEmptyString(str: string): boolean {
        let empty = str && str.length > 0; // Or any other logic, removing whitespace, etc.
        return !empty;
    }
    public isEmptyArray(data: any[]): boolean {
        if (null == data) return true;
        if (data.length < 1) return true;
        return false;
    }

    // - J W T   D E C O D E
    public JWTDecode2AccountName(codedToken: string): string {
        const token = this.JWTDecode(codedToken);
        return token["accountName"];
    }
    public JWTDecode2UniqueId(codedToken: string): string {
        const token = this.JWTDecode(codedToken);
        return token["uniqueId"];
    }
    // - J W T
    public JWTDecode(token: string): any {
        return jwt_decode(token);
    }
}
