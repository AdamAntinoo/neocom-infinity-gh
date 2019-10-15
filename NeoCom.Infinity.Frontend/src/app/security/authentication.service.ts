// - CORE
import { Injectable } from '@angular/core';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
// - DOMAIN
import { Credential } from '../domain/Credential';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {
    // - C O N S T R U C T O R
    constructor(protected isolationService: IsolationService) { }

    public isLoggedIn(): boolean {
        return
    }
    public isExpiredToken(): boolean {
        let expirationTime: number = this.isolationService.getFromStorage(this.isolationService.JWTTOKEN_EXPIRATION_TIME_KEY);
        let currentTime = +new Date();
        return (expirationTime < currentTime);
    }
}
