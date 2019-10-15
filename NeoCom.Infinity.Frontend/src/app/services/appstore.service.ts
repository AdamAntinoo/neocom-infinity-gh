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
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Credential } from '../domain/Credential';

@Injectable({
    providedIn: 'root'
})
export class AppStoreService {
    private credential: Credential;

    constructor(
        protected router: Router,
        protected backendService: BackendService) {}

    // - G L O B A L   S T O R E
    public setCredential(newCredential: Credential): void {
        this.credential = newCredential;
    }

    // - E N V I R O N M E N T    C A L L S
    public getApplicationName(): string {
        return this.backendService.getApplicationName();
    }
    public getApplicationVersion(): string {
        return this.backendService.getApplicationVersion();
    }
    public inDevelopment(): boolean {
        return this.backendService.inDevelopment();
    }
    public showExceptions(): boolean {
        return this.backendService.showExceptions();
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
