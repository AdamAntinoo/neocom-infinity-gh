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
        // browser.waitForAngularEnabled(true);
        // browser.waitForAngular();
        const urlRequest = 'dashboard';
        console.log('>[DashboardHomePage.navigateTo]> Navigating to page: ' + urlRequest);
        // browser.driver.sleep(3000);
        browser.get(browser.baseUrl + urlRequest) as Promise<any>;
        browser.driver.sleep(5000);
        browser.waitForAngular();
        // browser.waitForAngular();
    }
}
