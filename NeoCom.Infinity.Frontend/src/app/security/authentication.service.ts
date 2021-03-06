// - CORE
import { Injectable } from '@angular/core';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { TimeUnit } from '@app/platform/TimeUnit';
import { neocom_constants } from '@app/platform/neocom-constants.platform';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    // - C O N S T R U C T O R
    constructor(protected isolationService: IsolationService) { }

    // - J W T   D E C O D E
    public isLoggedIn(): boolean {
        const jwtToken = this.isolationService.getFromSession(neocom_constants.JWTTOKEN_KEY);
        console.log('-[AuthenticationService.isLoggedIn]> jwtToken: ' + jwtToken);
        if (jwtToken)
            if (!this.isExpiredToken()) return true;
        return false;
    }
    public isExpiredToken(): boolean {
        console.log('-[AuthenticationService.isExpiredToken]> expiration time: ' +
            this.isolationService.getFromSession(
                neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY));
        console.log('-[AuthenticationService.isExpiredToken]> expiration date: ' +
            new Date(this.isolationService.getFromSession(
                neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY)));
        const expirationTime: number = +new Date(this.isolationService.getFromSession(
            neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY));
        console.log('-[AuthenticationService.isExpiredToken]> expiration time: ' + expirationTime);
        if (Number.isNaN(expirationTime)) return true;
        const currentTime = +new Date();
        return (expirationTime < currentTime);
    }
    public storeJwtToken(newToken: string): void {
        this.isolationService.setToSession(neocom_constants.JWTTOKEN_KEY, newToken);
        const expirationTime = this.isolationService.dateAdd(Date.now(), 'hour', 1);
        console.log('-[AuthenticationService.isExpiredToken]> setting expiration time: ' + expirationTime);
        this.isolationService.setToSession(neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY, expirationTime);
    }
    public timestampJwtToken(deviation: number): void {
        const expirationTime = this.isolationService.dateAdd(this.isolationService.dateAdd(
            Date.now(), 'hour', 1),
            'second', deviation);
        this.isolationService.setToSession(neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY, expirationTime);
    }
    public clearJwtToken(): void {
        this.isolationService.removeFromSession(neocom_constants.JWTTOKEN_KEY);
        this.isolationService.removeFromSession(neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY);
    }
    public JWTDecode2AccountName(codedToken: string): string {
        const token = this.isolationService.JWTDecode(codedToken);
        return token["accountName"];
    }
    public JWTDecode2UniqueId(codedToken: string): string {
        const token = this.isolationService.JWTDecode(codedToken);
        return token["uniqueId"];
    }
}
