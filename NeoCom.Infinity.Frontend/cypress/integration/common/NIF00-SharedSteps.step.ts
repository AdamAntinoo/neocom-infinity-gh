// - CYPRESS
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - PAGE OBJECTS
import { AppInfoPanel } from "../../support/page-objects/AppInfoPanel.panel";
import { ServerInfoPanel } from "../../support/page-objects/ServerInfoPanel.panel";
import { CorporationDataPanel } from "../../support/page-objects/CorporationDataPanel.panel";
import { PilotPublicDataPanel } from "../../support/page-objects/PilotPublicDataPanel.panel";
import { PilotRenderPanel } from '../../support/page-objects/PilotRender.panel';

let appInfoPanel: AppInfoPanel;
let serverInfoPanel: ServerInfoPanel;
let corporationDataPanel: CorporationDataPanel;
let pilotPublicDataPanel: PilotPublicDataPanel;
let pilotRender: PilotRenderPanel;

Given('one instance of AppInfoPanel', function () {
    appInfoPanel = new AppInfoPanel();
});
Given('one instance of ServerInfoPanel', function () {
    serverInfoPanel = new ServerInfoPanel();
});
Given('one instance of CorporationDataPanel', function () {
    corporationDataPanel = new CorporationDataPanel();
});
Given('one instance of PilotPublicDataPanel', function () {
    pilotPublicDataPanel = new PilotPublicDataPanel();
});
Given('one instance of PilotRenderPanel', function () {
    pilotRender = new PilotRenderPanel();
});

Then('there is a {string} with the next fields', (panelType, dataTable) => {
    console.log('[THEN] there is a {string} with the next fields');
    const row = dataTable.hashes()[0];
    switch (panelType) {
        case 'appinfo-panel':
            appInfoPanel.validatePanel(row);
            break;
        case 'serverinfo-panel':
            serverInfoPanel.validatePanel(row);
            break;
        case 'corporation-public-data-panel':
            corporationDataPanel.validatePanel(row);
            break;
        case 'pilot-public-data-panel':
            pilotPublicDataPanel.validatePanel(row);
            break;
        case 'pilot-render':
            pilotRender.validatePanel(row);
            break;
    }
});
