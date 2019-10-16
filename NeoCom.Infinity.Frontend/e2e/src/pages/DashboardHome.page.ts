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

    public getCorporationName(): Promise<string> {
        return element(by.css('.corporation-name')).getText() as Promise<string>;
    }
}
