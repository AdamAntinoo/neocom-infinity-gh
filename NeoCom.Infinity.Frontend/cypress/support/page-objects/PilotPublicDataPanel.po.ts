// - SUPPORT
import { IsolationService } from "../IsolationService.support";
import { cy } from "date-fns/locale";

export class PilotPublicDataPanel extends IsolationService {
    public validateAppInfoPanel(row: any) {
        throw new Error("Method not implemented.");
    }
    private getServerStatus(): vid {
        cy.get element(by.css('#server-status')).getText() as Promise<string>;
    }
}
