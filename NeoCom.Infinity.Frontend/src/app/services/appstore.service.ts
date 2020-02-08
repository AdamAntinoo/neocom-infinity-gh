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
import { HttpClientWrapperService } from '@app/services/httpclientwrapper.service';
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
        protected backendService: BackendService,
        protected httpService: HttpClientWrapperService) {
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
        console.log('-[AppStoreService.downloadCorporation]> Starting to download corporation id: ' + corporationId);
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

    // - G L O B A L   S U P P O R T   M E T H O D S
    public isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length == 0) return true;
        if (target.length == 0) return true;
        return false;
    }
    public accessProperties(propertyName: string): Observable<any> {
        console.log("><[AppStoreServiceDefinitions.propertiesTreatments]");
        // Construct the request to call the backend.
        let request = '/assets/properties/' + propertyName + '.json';
        return this.httpService.wrapHttpRESOURCECall(request)
            .pipe(data => {
                return data as any;
            });
    }


    // - J W T   D E C O D E
    public JWTDecode2AccountName(codedToken: string): string {
        const token = this.isolationService.JWTDecode(codedToken);
        return token["accountName"];
    }
    public JWTDecode2UniqueId(codedToken: string): string {
        const token = this.isolationService.JWTDecode(codedToken);
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
