// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
import { SharedPanelsElements } from './SharedPanelsElements.page';
import { HeaderPanelsElements } from './HeaderPanelsElements.page';

export class DashboardHomePage extends SharedPanelsElements {
    private headerPanels: HeaderPanelsElements;
    private pageName: string;
    constructor() {
        super();
        this.setPageName('Dashboard Home Page')
    }
    public getPageName(): string {
        return this.pageName as string;
    }
    public setPageName(newName: string): string {
        this.pageName = newName;
        return this.pageName;
    }
    public getServerName(): Promise<string> {
        return this.headerPanels.getServerName();
    }

    public navigateTo() {
        browser.waitForAngularEnabled(false);
        const urlRequest = 'dashboard';
        console.log('>[DashboardHomePage.navigateTo]> Navigating to page: ' + urlRequest);
        return browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    }
}
