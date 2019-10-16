// - CORE
import { Injectable } from '@angular/core';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { TimeUnit } from '@app/platform/TimeUnit';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    // - C O N S T R U C T O R
    constructor(protected isolationService: IsolationService) { }

    // - J W T   D E C O D E
    public isLoggedIn(): boolean {
        const jwtToken = this.isolationService.getFromStorage(this.isolationService.JWTTOKEN_KEY);
        if (jwtToken)
            if (!this.isExpiredToken()) return true;
        return false;
    }
    public isExpiredToken(): boolean {
        const expirationTime: number = 0+this.isolationService.getFromStorage(this.isolationService.JWTTOKEN_EXPIRATION_TIME_KEY);
        console.log('--[AuthenticationService.isExpiredToken]> expiration time: ' + expirationTime);
        const currentTime = +new Date();
        return (expirationTime < currentTime);
    }
    public storeJwtToken(newToken: string): void {
        this.isolationService.setToStorage(this.isolationService.JWTTOKEN_KEY, newToken);
        const expirationTime = +new Date() + new TimeUnit(TimeUnit.HOUR).toMillis(1);
        this.isolationService.setToStorage(this.isolationService.JWTTOKEN_EXPIRATION_TIME_KEY, expirationTime);
    }
    public timestampJwtToken(deviation: number): void {
        const expirationTime = +new Date() + new TimeUnit(TimeUnit.HOUR).toMillis(1) + deviation;
        this.isolationService.setToStorage(this.isolationService.JWTTOKEN_EXPIRATION_TIME_KEY, expirationTime);
    }
    public clearJwtToken(): void {
        this.isolationService.removeFromStorage(this.isolationService.JWTTOKEN_KEY);
        this.isolationService.removeFromStorage(this.isolationService.JWTTOKEN_EXPIRATION_TIME_KEY);
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