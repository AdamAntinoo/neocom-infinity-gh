import { Injectable } from '@angular/core';
import { environment } from '@env/environment';

@Injectable({
    providedIn: 'root'
})
export class SupportIsolationService {
    private storage = new Map();

    // - E N V I R O N M E N T   A C C E S S
    public getAppName(): string {
        return environment.appName;
    }
    public getAppVersion(): string {
        return environment.appVersion;
    }

    // - S T O R A G E
    public getFromStorage(_key: string): any {
        return this.storage.get(_key);
    }
    public setToStorage(_key: string, _content: any): any {
        return this.storage.set(_key, _content)
    }
}
