import { browser, by, element } from 'protractor';
import { Panel } from '../support/panel';

export class LoginValidationPage {
    private PAGE_NAME = 'Login Validation Page';
    // private appinfo: any;
    // private loginvalidationprogress: any;
    // private loginvalidationexception: any;

    public navigateTo(code: string, state: string) {
        const urlRequest = '/loginValidation?code=' + code + '&state=' + state;
        console.log('>[LoginValidationPage]>Navigating to page: ' + urlRequest);
        return browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    }

    public getPageName(): string {
        return this.PAGE_NAME as string;
    }
    public getPanelCount(): number {
        let panelCount = 0;
        if (null != this.getPagePanelById('appinfo-panel')) panelCount++;
        if (null != this.getPagePanelById('loginvalidationprogress-panel')) panelCount++;
        if (null != this.getPagePanelById('loginvalidationexception-panel')) panelCount++;
        return panelCount;
    }
    public getPagePanelById(panelIdentifier: string): any {
        console.log('>[LoginValidationPage.getPagePanelById]>Searching for panel: ' + panelIdentifier);
        console.log('>[LoginValidationPage.getPagePanelById]>Panel found: ' + element(by.id(panelIdentifier)));
        return element(by.id(panelIdentifier));
    }
    // public getAppInfoPanelType(): string {
    //   let panel = element(by.css('.app-name')).getText();
    //   if (null != panel) return panel.type;
    //   else return 'undefined';
    // }
    // public search4PanelType(panelType: string): Panel {
    //   const panels = element(by.css('.app-name')).all;
    //   for (let panel: any of panels.) {
    //     if (panel.type === panelType) {
    //       return new Panel("Data");
    //     }
    //   }
    //   return null;
    // }
    getTitleText() {
        return element(by.css('app-root .content span')).getText() as Promise<string>;
    }
    getAppName() {
        return element(by.css('.app-name')).getText() as Promise<string>;
    }
    getAppVersion() {
        return element(by.css('.app-version')).getText() as Promise<string>;
    }
    getCopyright() {
        return element(by.css('.app-copyright')).getText() as Promise<string>;
    }
}
