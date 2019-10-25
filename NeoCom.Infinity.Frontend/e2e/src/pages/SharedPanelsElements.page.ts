// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
import { AcceptanceCoreFunctionality } from '../support/AcceptanceCoreFunctionality.support';

export class SharedPanelsElements extends AcceptanceCoreFunctionality {
    // - APP INFO PANEL
    public getAppName(): Promise<string> {
        return element(by.css('.app-name')).getText() as Promise<string>;
    }
    public getAppVersion(): Promise<string> {
        return element(by.css('.app-version')).getText() as Promise<string>;
    }
    public getCopyright(): Promise<string> {
        return element(by.css('.app-copyright')).getText() as Promise<string>;
    }
}
