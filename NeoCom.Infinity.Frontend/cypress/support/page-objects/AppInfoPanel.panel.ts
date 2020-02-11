// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const APP_NAME: string = 'app-name';
const APP_VERSION: string = 'app-version';
const COPYRIGHT: string = 'app-copyright';

export class AppInfoPanel extends IsolationService {
    public validatePanel(row: any) {
        let appName = this.decodeDataTableRow(row, APP_NAME).toUpperCase();
        console.log('[AppInfoPanel.validateAppInfoPanel]> APP_NAME=' + appName);
        let appVersion = this.decodeDataTableRow(row, APP_VERSION);
        console.log('[AppInfoPanel.validateAppInfoPanel]> APP_VERSION=' + appVersion);
        let copyright = this.decodeDataTableRow(row, COPYRIGHT);
        console.log('[AppInfoPanel.validateAppInfoPanel]> COPYRIGHT=' + copyright);
        // cy.get('app-info-panel').within(($panel) => {
        cy.get('.app-name').contains(appName);
        cy.get('.app-version').contains(appVersion);
        cy.get('.app-copyright').contains(copyright);
        // });
    }
}
