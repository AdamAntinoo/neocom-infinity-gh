// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
export class LoginValidationPage {
    private JWTTOKEN_KEY: string;
    private PAGE_NAME = 'Login Validation Page';

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
    public getExceptionMessage(): Promise<string> {
        return element(by.css('.exception-message')).getText() as Promise<string>;
    }
}
