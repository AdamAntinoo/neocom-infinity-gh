// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';

export class AcceptanceCoreFunctionality {
    public getPagePanelById(panelIdentifier: string): any {
        console.log('>[AcceptanceCoreFunctionality.getPagePanelById]> Searching for panel: ' + panelIdentifier);
        return element(by.id(panelIdentifier));
    }
}
