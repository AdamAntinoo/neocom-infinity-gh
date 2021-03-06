// - CORE
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
// - ENVIRONMENT
import { environment } from '@env/environment';
// - WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
import * as jwt_decode from 'jwt-decode';
// - NOTIFICATIONS
import { ToastrManager } from 'ng6-toastr-notifications';
// - ROUTER
import { Router } from '@angular/router';

/**
 * The responsibility for this service is to isolate the internal application api from the external implementation when using libraries that can change over time like the Toaster. It will also provide a simplification when accessing some common features like the environment, global functions or storage.
 */
@Injectable()
export class IsolationService {
    // - C O N S T A N T S
    public JWTTOKEN_KEY: string;
    public JWTTOKEN_EXPIRATION_TIME_KEY: string;
    // - C O N S T R U C T O R
    constructor(
        @Inject(LOCAL_STORAGE) protected storage: WebStorageService,
        @Inject(SESSION_STORAGE) protected sessionStorage: WebStorageService,
        protected notifier: ToastrManager,
        //  protected router: Router
    ) {
        //   this.JWTTOKEN_KEY = environment.JWTTOKEN_KEY;
        //   this.JWTTOKEN_EXPIRATION_TIME_KEY = environment.JWTTOKEN_EXPIRATION_TIME_KEY;
    }

    // - R O U T I N G   W R A P
    //  public route2FirstPage(): void {
    //      this.router.navigate(['loginValidation']);
    //  }

    // - E N V I R O N M E N T   A C C E S S
    //  public getServerName(): string {
    //      return environment.serverName;
    //  }
    //  public getApiV1(): string {
    //      return environment.apiVersion1;
    //  }
    //  public getApiV2(): string {
    //      return environment.apiVersion2;
    //  }
    //  public getAppName(): string {
    //      return environment.appName;
    //  }
    //  public getAppVersion(): string {
    //      return environment.appVersion;
    //  }
    //  public inDevelopment(): boolean {
    //      return !environment.production;
    //  }
    //  public getMockStatus(): boolean {
    //      return environment.mockStatus;
    //  }
    //  public showExceptions(): boolean {
    //      return environment.showexceptions;
    //  }

    // - J W T
    public JWTDecode(token: string): any {
        return jwt_decode(token);
    }

    // - S T O R A G E
    public getFromStorage(_key: string): any {
        return this.storage.get(_key);
    }
    public setToStorage(_key: string, _content: any): any {
        return this.storage.set(_key, _content)
    }
    public removeFromStorage(_key: string): any {
        this.storage.remove(_key);
    }
    public getFromSession(_key: string): any {
        console.log('>[AppStoreService.getFromSession]> key: ' + _key);
        return this.sessionStorage.get(_key);
    }
    public setToSession(_key: string, _content: any): any {
        return this.sessionStorage.set(_key, _content)
    }
    public removeFromSession(_key: string): any {
        this.sessionStorage.remove(_key);
    }

    // - N O T I F I C A T I O N S
    private notifierConfiguration: any = {
        toastTimeout: 5000,
        newestOnTop: true,
        position: 'bottom-right',
        messageClass: 'notifier-message',
        titleClass: 'notifier-title',
        animate: 'slideFromLeft'
    };
    public successNotification(_message: string, _title?: string, _options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != _options) notConf = { ...this.notifierConfiguration, ..._options };
        else notConf = this.notifierConfiguration;
        this.notifier.successToastr(_message, _title, notConf);
    }
    public errorNotification(_message: string, _title?: string, _options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != _options) notConf = { ...this.notifierConfiguration, ..._options };
        else notConf = this.notifierConfiguration;
        notConf.toastTimeout = 15000;
        this.notifier.errorToastr(_message, _title, notConf);
    }
    public warningNotification(_message: string, _title?: string, _options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != _options) notConf = { ...this.notifierConfiguration, ..._options };
        else notConf = this.notifierConfiguration;
        this.notifier.warningToastr(_message, _title, notConf);
    }
    public infoNotification(_message: string, _title?: string, _options?: any): void {
        // Join options configuration.
        let notConf;
        if (null != _options) notConf = { ...this.notifierConfiguration, ..._options };
        else notConf = this.notifierConfiguration;
        this.notifier.infoToastr(_message, _title, notConf);
    }

    // - U T I L I T I E S
    /**
 * Adds time to a date. Modelled after MySQL DATE_ADD function.
 * Example: dateAdd(new Date(), 'minute', 30)  //returns 30 minutes from now.
 * https://stackoverflow.com/a/1214753/18511
 *
 * @param date  Date to start with
 * @param interval  One of: year, quarter, month, week, day, hour, minute, second
 * @param units  Number of units of the given interval to add.
 */
    public dateAdd(date, interval, units) {
        var ret = new Date(date); //don't change original date
        var checkRollover = function () { if (ret.getDate() != date.getDate()) ret.setDate(0); };
        switch (interval.toLowerCase()) {
            case 'year': ret.setFullYear(ret.getFullYear() + units); checkRollover(); break;
            case 'quarter': ret.setMonth(ret.getMonth() + 3 * units); checkRollover(); break;
            case 'month': ret.setMonth(ret.getMonth() + units); checkRollover(); break;
            case 'week': ret.setDate(ret.getDate() + 7 * units); break;
            case 'day': ret.setDate(ret.getDate() + units); break;
            case 'hour': ret.setTime(ret.getTime() + units * 3600000); break;
            case 'minute': ret.setTime(ret.getTime() + units * 60000); break;
            case 'second': ret.setTime(ret.getTime() + units * 1000); break;
            default: ret = undefined; break;
        }
        return ret;
    }
}
