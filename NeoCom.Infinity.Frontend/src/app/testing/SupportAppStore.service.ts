// - CORE
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { Subject } from 'rxjs';
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
import { Credential } from '@app/domain/Credential.domain';
import { Corporation } from '@app/domain/Corporation.domain';
import { Pilot } from '@app/domain/Pilot.domain';
import { CorporationDataResponse } from '@app/domain/dto/CorporationDataResponse.dto';

@Injectable({
    providedIn: 'root'
})
export class SupportAppStoreService {
    private credential: Credential;
    private corporationActiveCache: Subject<Corporation | Corporation[]> = new Subject();
    private pilotActiveCache: Subject<Pilot | Pilot[]> = new Subject();

    constructor(
        // protected router: Router,
        protected isolation: IsolationService,
        // protected backendService: BackendService
    ) {
    }

    // - M O C K   D A T A   A C C E S S
    public directAccessMockResource(dataIdentifier: string): any | any[] {
        console.log(">>[SupportAppStoreService.directAccessMockResource]> dataIdentifier: " + dataIdentifier);
        let rawdata = require('./mock-data/' + dataIdentifier.toLowerCase() + '.json');
        return rawdata;
    }
    // - G L O B A L   A C C E S S   M E T H O D S
    public isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length > 0) return false;
        if (target.length > 0) return true;
        return true;
    }

    // - S T O R E   A C C E S S   S E C T I O N
    /**
     * Resets and clears the cached stored contents so on next login we should reload all data.
     */
    public clearStore(): void {
        // Clear dynamic caches.
        this.corporationActiveCache.next(null);
    }
    // - C O R P O R A T I O N
    public accessCorporation(): Observable<Corporation | Corporation[]> {
        return this.corporationActiveCache;
    }
    public downloadCorporation(corporation: Corporation): void {
        this.corporationActiveCache.next(corporation);
    }
    // - P I L O T
    public accessPilot(): Observable<Pilot | Pilot[]> {
        return this.pilotActiveCache;
    }

    // - S T O R E   D A T A   D O W N L O A D E R S
    // public downloadCorporation(corporationId: number): void {
    //    return this.backendService.apiGetCorporationPublicData_v1(corporationId)
    //       .pipe(map((corporationResponse: CorporationDataResponse) => {
    //          let corporation = new Corporation(corporationResponse.corporation);
    //          return corporation;
    //       }));
    // }
    // private downloadPilot(pilotId: number): Observable<Pilot> {
    //    return this.backendService.apiGetPilotPublicData_v1(pilotId)
    //       .pipe(map((response: Pilot) => {
    //          let pilot = response;
    //          return pilot;
    //       }));
    // }

    // - E N V I R O N M E N T    C A L L S
    //  public getApplicationName(): string {
    //      return environment.appName;
    //  }
    //  public getApplicationVersion(): string {
    //      return environment.appVersion;
    //  }

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
