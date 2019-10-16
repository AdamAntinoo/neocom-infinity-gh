// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
import { SharedFunctionalityPage } from './SharedFunctionality.page';

export class DashboardHomePage extends SharedFunctionalityPage {
    constructor() {
        super();
        this.setPageName('Dashboard Home Page')
    }

    public navigateTo(feature: string) {
        const urlRequest = '/dashboard/' + feature;
        console.log('>[DashboardHomePage.navigateTo]>Navigating to page: ' + urlRequest);
        return browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    }
    // - SERVER STATUS PANEL
    public getServerName(): Promise<string> {
        return element(by.css('.server-name')).getText() as Promise<string>;
    }
    public getServerStatus(): Promise<string> {
        return element(by.css('.server-status')).getText() as Promise<string>;
    }
    public getCapsuleers(): Promise<string> {
        return element(by.css('.server-capsuleers')).getText() as Promise<string>;
    }
    // - CORPORATION PUBLIC DATA PANEL
    public getCorporationName(): Promise<string> {
        return element(by.css('.corporation-name')).getText() as Promise<string>;
    }
    // - PILOT PUBLIC DATA PANEL
    public getPilotName(): Promise<string> {
        return element(by.css('.pilot-name')).getText() as Promise<string>;
    }
}
