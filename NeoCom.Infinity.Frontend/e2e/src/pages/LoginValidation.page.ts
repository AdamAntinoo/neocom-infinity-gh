// - CORE
import { Inject } from '@angular/core';
import { environment } from '../support/environment';
// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
// - WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';

export class LoginValidationPage {
    private JWTTOKEN_KEY: string;
    private PAGE_NAME = 'Login Validation Page';

    // constructor(
    //     @Inject(LOCAL_STORAGE) protected storage: WebStorageService,
    //     @Inject(SESSION_STORAGE) protected sessionStorage: WebStorageService) {
    //     this.JWTTOKEN_KEY = environment.JWTTOKEN_KEY;
    // }

    public navigateTo(code?: string, state?: string) {
        const urlRequest = '/loginValidation?code=' + code + '&state=' + state;
        console.log('>[LoginValidationPage]>Navigating to page: ' + urlRequest);
        return browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    }

    public getPageName(): string {
        return this.PAGE_NAME as string;
    }
    public getPagePanelById(panelIdentifier: string): any {
        console.log('>[LoginValidationPage.getPagePanelById]>Searching for panel: ' + panelIdentifier);
        return element(by.id(panelIdentifier));
    }
    // getTitleText() {
    //     return element(by.css('app-root .content span')).getText() as Promise<string>;
    // }
    public getAppName() {
        return element(by.css('.app-name')).getText() as Promise<string>;
    }
    public getAppVersion() {
        return element(by.css('.app-version')).getText() as Promise<string>;
    }
    public getCopyright() {
        return element(by.css('.app-copyright')).getText() as Promise<string>;
    }
    public getProgressLabel(): Promise<string> {
        return element(by.css('.progress-label')).getText() as Promise<string>;
    }
    public getProgressCode(): Promise<string> {
        return element(by.css('.progress-code')).getText() as Promise<string>;
    }
    public readStorage(key: string): any {
        // const data = this.storage.get(key);
        // if (null != data) return JSON.parse(data);
        // else return null;
    }
}
