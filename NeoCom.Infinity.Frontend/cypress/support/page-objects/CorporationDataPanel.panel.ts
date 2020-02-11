// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const SERVER_NAME: string = 'server-name';
const SERVER_STATUS: string = 'server-status';
const SERVER_CAPSULEERS: string = 'server-capsuleers';

export class CorporationDataPanel extends IsolationService {
    public validatePanel(row: any) {
        let serverName = this.decodeDataTableRow(row, SERVER_NAME);
        console.log('[ServerInfoPanel.validateServerInfoPanel]> SERVER_NAME=' + serverName);
        cy.get('#server-name').contains(serverName);
        let serverStatus = this.decodeDataTableRow(row, SERVER_STATUS).toUpperCase();
        console.log('[ServerInfoPanel.validateServerInfoPanel]> SERVER_STATUS=' + serverStatus);
        cy.get('#server-status').contains(serverStatus);
        let capsuleers = this.decodeDataTableRow(row, SERVER_CAPSULEERS);
        console.log('[AppInfoPanel.validateAppInfoPanel]> SERVER_CAPSULEERS=' + capsuleers);
        cy.get('#server-capsuleers').contains(capsuleers);
    }
}
