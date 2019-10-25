// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
// - ENVIRONMENT
import { environment } from '@env/environment';
// - ROUTER
import { Router } from '@angular/router';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
import { ActiveCacheWrapper } from '@app/modules/shared/support/ActiveCacheWrapper';
// - DOMAIN
import { Credential } from '../domain/Credential.domain';
import { IsolationService } from '@app/platform/isolation.service';
import { NeoComException } from '@app/platform/NeoComException';
import { ExceptionCatalog } from '@app/platform/ExceptionCatalog';
import { Corporation } from '@app/domain/Corporation.domain';
import { CorporationDataResponse } from '@app/domain/dto/CorporationDataResponse.dto';
import { Pilot } from '@app/domain/Pilot.domain';
import { ResponseTransformer } from './support/ResponseTransformer';

@Injectable({
    providedIn: 'root'
})
export class AppStoreService {
    // - S T O R E   D A T A   S E C T I O N
    private corporationActiveCache: ActiveCacheWrapper<Corporation>;
    private pilotActiveCache: ActiveCacheWrapper<Pilot>;

    private lastInterceptedException: NeoComException;

    constructor(
        protected router: Router,
        protected isolationService: IsolationService,
        protected backendService: BackendService) {
        // - S T O R E   D A T A   S E C T I O N
        this.corporationActiveCache = new ActiveCacheWrapper<Corporation>()
            .setTimedCache(false) // Contents do not expire.
            .setReturnObsoletes(false) // If the content is expired then wait for a new request.
            .setDownloader((): Observable<Corporation | Corporation[]> => {
                const corporationId = this.getCorporationIdentifier();
                return this.downloadCorporation(corporationId);
            });
        this.pilotActiveCache = new ActiveCacheWrapper<Pilot>()
            .setTimedCache(false) // Contents do not expire.
            .setReturnObsoletes(false) // If the content is expired then wait for a new request.
            .setDownloader((): Observable<Pilot | Pilot[]> => {
                const pilotId = this.getPilotIdentifier();
                return this.downloadPilot(pilotId);
            });
    }

    // - R O U T I N G
    public route2Destination(page: string): void {
        this.router.navigate([page]);
    }

    // - S T O R E   D A T A   D O W N L O A D E R S
    private downloadCorporation(corporationId: number): Observable<Corporation> {
        return this.backendService.apiGetCorporationPublicData_v1(corporationId,
            new ResponseTransformer().setDescription('Do response transformation to "Corporation".')
                .setTransformation((data: any): Corporation => {
                    return new Corporation(data);
                }))
            .pipe(map((corporation: Corporation) => {
                return corporation;
            }));
    }
    private downloadPilot(pilotId: number): Observable<Pilot> {
        return this.backendService.apiGetPilotPublicData_v1(pilotId)
            .pipe(map((response: Pilot) => {
                let pilot = response;
                return pilot;
            }));
    }

    // - G L O B A L   S T O R E
    public accessCredential(): Credential {
        return this.getCredential();
    }
    public getCredential(): Credential {
        const credentialJson = this.isolationService.getFromSession(environment.CREDENTIAL_KEY);
        if (null == credentialJson) throw new NeoComException(ExceptionCatalog.AUTHORIZATION_MISSING)
        const credential = new Credential(JSON.parse(credentialJson));
        return credential;
    }
    public getCorporationIdentifier(): number {
        return this.getCredential().getCorporationId();
    }
    public getPilotIdentifier(): number {
        return this.getCredential().getAccountId();
    }
    public getLastInterceptedException(): NeoComException {
        return this.lastInterceptedException;
    }
    public setLastInterceptedException(exception: NeoComException): void {
        this.lastInterceptedException = exception;
    }

    // - S T O R E   A C C E S S   S E C T I O N
    /**
     * Resets and clears the cached stored contents so on next login we should reload all data.
     */
    public clearStore(): void {
        // Clear dynamic caches.
        this.corporationActiveCache.clear();
    }
    // - C O R P O R A T I O N
    public accessCorporation(): Observable<Corporation | Corporation[]> {
        return this.corporationActiveCache.accessData();
    }
    // - P I L O T
    public accessPilot(): Observable<Pilot | Pilot[]> {
        return this.pilotActiveCache.accessData();
    }

    // - E N V I R O N M E N T    C A L L S
    // public getApplicationName(): string {
    //    return this.backendService.getApplicationName();
    // }
    // public getApplicationVersion(): string {
    //    return this.backendService.getApplicationVersion();
    // }
    // public inDevelopment(): boolean {
    //    return this.backendService.inDevelopment();
    // }
    // public showExceptions(): boolean {
    //    return this.backendService.showExceptions();
    // }

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
        const token = this.backendService.isolation.JWTDecode(codedToken);
        return token["accountName"];
    }
    public JWTDecode2UniqueId(codedToken: string): string {
        const token = this.backendService.isolation.JWTDecode(codedToken);
        return token["uniqueId"];
    }

    // - N O T I F I C A T I O N S
    public successNotification(_message: string, _title?: string, _options?: any): void {
        this.backendService.isolation.successNotification(_message, _title, _options);
    }
    public errorNotification(_message: string, _title?: string, _options?: any): void {
        this.backendService.isolation.errorNotification(_message, _title, _options);
    }
    public warningNotification(_message: string, _title?: string, _options?: any): void {
        this.backendService.isolation.warningNotification(_message, _title, _options);
    }
    public infoNotification(_message: string, _title?: string, _options?: any): void {
        this.backendService.isolation.infoNotification(_message, _title, _options);
    }
}  
