// - PROTRACTOR
// import { by } from 'protractor';
// import { element } from 'protractor';
// // - ASSERTION
// import { expect } from 'chai';
import { IsolationService } from '../IsolationService.support';

const APP_NAME: string = 'app-name';
const APP_VERSION: string = 'app-version';
const COPYRIGHT: string = 'app-copyright';

export class AppInfoPanel extends IsolationService {

    public validateAppInfoPanel(row: any): Promise<boolean> {
        let appName = this.decodeDataTableRow(row, APP_NAME).toUpperCase();
        console.log('[AppInfoPanel.validateAppInfoPanel]> APP_NAME=' + appName);
        expect(await this.getAppName()).to.equal(appName);
        let appVersion = this.decodeDataTableRow(row, APP_VERSION);
        console.log('[AppInfoPanel.validateAppInfoPanel]> APP_VERSION=' + appVersion);
        expect(await this.getAppVersion()).to.equal(appVersion);
        let copyright = this.decodeDataTableRow(row, COPYRIGHT);
        console.log('[AppInfoPanel.validateAppInfoPanel]> COPYRIGHT=' + copyright);
        expect(await this.getAppCopyright()).to.equal(copyright);

        return Promise.resolve(true);
    }
    public getAppName() {
        return element(by.css('.app-name')).getText() as Promise<string>;
    }
    public getAppVersion(): Promise<string> {
        return element(by.css('.app-version')).getText() as Promise<string>;
    }
    public getAppCopyright(): Promise<string> {
        return element(by.css('.app-copyright')).getText() as Promise<string>;
    }
}
