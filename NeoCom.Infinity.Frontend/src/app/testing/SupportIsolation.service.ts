import { Injectable } from '@angular/core';
import { environment } from '@env/environment';

@Injectable({
    providedIn: 'root'
})
export class SupportIsolationService { 
    public getAppName(): string {
        return environment.appName;
    }
    public getAppVersion(): string {
        return environment.appVersion;
    }
}
