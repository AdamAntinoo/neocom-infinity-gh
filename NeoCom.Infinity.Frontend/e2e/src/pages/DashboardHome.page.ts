// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
import { SharedPanelsElements } from './SharedPanelsElements.page';

export class DashboardHomePage extends SharedPanelsElements {
    constructor() {
        super();
        this.setPageName('Dashboard Home Page')
    }

    private pageName: string;

    public getPageName(): string {
        return this.pageName as string;
    }
    public setPageName(newName: string): string {
        this.pageName = newName;
        return this.pageName;
    }


    public navigateTo() {
        const urlRequest = '/dashboard';
        console.log('>[DashboardHomePage.navigateTo]> Navigating to page: ' + urlRequest);
        return browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    }
}
