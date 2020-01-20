// - SUPPORT
import { IsolationService } from "../IsolationService.support";
// import { cy } from "date-fns/locale";
// - FIELDS
const PILOT_HEADER = 'pilot-header';

export class PilotPublicDataPanel extends IsolationService {
    // private panelPO: PilotPublicDataPageObject;
    // constructor(values: Object = {}) {
    //     super();
    //     Object.assign(this, values);
    //     this.panelPO = new PilotPublicDataPageObject();
    // }
    public validatePanel(row: any) {
        let pilotHeder = this.decodeDataTableRow(row, PILOT_HEADER).toUpperCase();
        console.log('[PilotPublicDataPanel.validatePanel]> PILOT_HEADER=' + pilotHeder);
        PilotPublicDataPageObject.checkPilotHeader(pilotHeder);
    }
    // private getServerStatus(): void {
    //     cy.get('app-info-panel')

    //     cy.get element(by.css('#server-status')).getText() as Promise<string>;
    // }
}
