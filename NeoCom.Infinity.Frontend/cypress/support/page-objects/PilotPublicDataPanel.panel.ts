// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const PILOT_HEADER = 'pilot-header';

export class PilotPublicDataPanel extends IsolationService {
    public validatePanel(row: any): void {
        let pilotHeder = this.decodeDataTableRow(row, PILOT_HEADER).toUpperCase();
        console.log('[PilotPublicDataPanel.validatePanel]> PILOT_HEADER=' + pilotHeder);
        cy.get('#pilot-header').contains(pilotHeder);
    }
}
