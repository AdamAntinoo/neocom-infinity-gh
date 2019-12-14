// - PROTRACTOR
import { by } from 'protractor';
import { element } from 'protractor';
// - ASSERTION
import { expect } from 'chai';
import { assert } from 'chai';
import { IsolationService } from '../support/IsolationService.support';

const APP_NAME: string = 'app-name';

export class AppInfoPanel extends IsolationService {

    public async validateAppInfoPanel(row: any): Promise<boolean> {
        let appName = this.decodeDataTableRow(row, APP_NAME).toUpperCase();
        console.log('[AppInfoPanel.validateAppInfoPanel]> APP_NAME=' + appName);
        expect(await this.getAppName()).to.equal(appName);
        return Promise.resolve(true);
    }
    private getAppName(): Promise<string> {
        return element(by.css('.app-name')).getText() as Promise<string>;
    }
}
