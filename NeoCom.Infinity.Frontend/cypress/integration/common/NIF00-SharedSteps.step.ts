// - CYPRESS
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - PAGE OBJECTS
// import { AppInfoPanel } from "../../support/page-objects/AppInfoPanel.panel";
// import { ServerInfoPanel } from "../../support/page-objects/ServerInfoPanel.panel";
import { PilotPublicDataPanel } from "../../support/page-objects/PilotPublicDataPanel.panel";
// import { DashboardHomePage } from '../pages/DashboardHome.page';
// import { IsolationService } from '../support/IsolationService.support';
// import { AppInfoPanel } from '../pages/AppInfoPanel.panel';
// import { ServerInfoPanel } from '../pages/ServerInfoPanel.panel';
// - DEFINITIONS
// let isolationService: IsolationService;
// let appInfoPanel: AppInfoPanel;
// let serverInfoPanel: ServerInfoPanel;
let pilotPublicDataPanel: PilotPublicDataPanel;

// Given('one instance of AppInfoPanel', function () {
//     appInfoPanel = new AppInfoPanel();
// });
// Given('one instance of ServerInfoPanel', function () {
//     serverInfoPanel = new ServerInfoPanel();
// });
Given('one instance of PilotPublicDataPanel', function () {
    pilotPublicDataPanel = new PilotPublicDataPanel();
});

//    ?When the page is activated with the request id "DASHBOARD-HOME-SUCCESS"
// Undefined.Implement with the following snippet:

// When('the page is activated with the request id {string}', function (string) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });

//    ?Then there is a "pilot-public-data-panel" with the next fields
//     | pilot - header |
//        | PILOT |
//     Undefined.Implement with the following snippet:

// Then('there is a {string} with the next fields', function (string, dataTable) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });

Then('there is a {string} with the next fields', (panelType, dataTable) => {
    console.log('[THEN] there is a {string} with the next fields');
    const row = dataTable.hashes()[0];
    switch (panelType) {
        case 'appinfo-panel':
            // appInfoPanel.validateAppInfoPanel(row);
            break;
        case 'serverinfo-panel':
            // browser.driver.sleep(1000);
            //  serverInfoPanel.validateServerInfoPanel(row);
            break;
        case 'pilot-public-data-panel':
            pilotPublicDataPanel.validatePanel(row);
            break;
    }
});
//    ?Then there is a "pilot-render" with the next fields
//     | pilot - name | pilot - id | pilot - race | pilot - sex |
//        | Beth Ripley | [92223647] | Minmatar - Workers - Brutor | FEMALE |
//     Undefined.Implement with the following snippet:

// Then('there is a {string} with the next fields', function (string, dataTable) {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });
