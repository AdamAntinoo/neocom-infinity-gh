// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { environment } from '@env/environment';

export class GlobalService {
    constructor(protected isolation: IsolationService) { }

    // - E N V I R O N M E N T    C A L L S
    public getApplicationName(): string {
        return this.getAppName();
    }
    public getApplicationVersion(): string {
        return this.getAppVersion();
    }
    // public inDevelopment(): boolean {
    //     return this.isolation.inDevelopment();
    // }
    // public showExceptions(): boolean {
    //     return this.isolation.showExceptions();
    // }
    public getAppName(): string {
        return environment.appName;
    }
    public getAppVersion(): string {
        return environment.appVersion;
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
}
