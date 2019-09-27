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
export class SupportAppStoreService extends GlobalService {
    constructor(
        protected isolation: IsolationService,
        // protected router: Router,
        protected backendService: BackendService) { super(isolation); }
 }
