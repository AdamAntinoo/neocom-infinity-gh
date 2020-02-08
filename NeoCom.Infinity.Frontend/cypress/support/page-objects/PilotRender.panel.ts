// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// - FIELDS
const PILOT_NAME = 'pilot-name';
const PILOT_ID = 'pilot-id';
const PILOT_RACE = 'pilot-race';
const PILOT_SEX = 'pilot-sex';

export class PilotRenderPanel extends IsolationService {
    public validatePanel(row: any) {
        let pilotName = this.decodeDataTableRow(row, PILOT_NAME);
        console.log('[PilotRenderPanel.validatePanel]> PILOT_NAME=' + pilotName);
        let pilotId = this.decodeDataTableRow(row, PILOT_ID);
        console.log('[PilotRenderPanel.validatePanel]> PILOT_ID=' + pilotId);
        let pilotGender = this.decodeDataTableRow(row, PILOT_SEX).toUpperCase();
        console.log('[PilotRenderPanel.validatePanel]> PILOT_SEX=' + pilotGender);
        cy.get('pilot-public-data-panel').find('pilot-render').within(($panel) => {
            cy.get('.pilot-name').contains(pilotName);
            cy.get('.pilot-id').contains(pilotId);
            cy.get('.pilot-gender').contains(pilotGender);
        });
    }
}
