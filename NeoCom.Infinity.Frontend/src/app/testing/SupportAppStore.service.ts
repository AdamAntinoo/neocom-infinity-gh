// - CORE
import { Injectable } from '@angular/core';
// - ENVIRONMENT
import { environment } from '@env/environment';
// - WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { GlobalService } from '@app/platform/global.service';
import { IsolationService } from '@app/platform/isolation.service';
import { BackendService } from '@app/services/backend.service';

@Injectable({
    providedIn: 'root'
})
export class SupportAppStoreService /*extends GlobalService*/ {
    constructor(
        // protected isolation: IsolationService,
        // protected router: Router,
        protected backendService: BackendService) {
        // super(isolation);
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
}
