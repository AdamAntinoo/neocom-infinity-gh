// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - COMPONENTS
import { AppInfoPanel } from "../../support/page-objects/AppInfoPanel.panel";
import { ServerInfoPanel } from "../../support/page-objects/ServerInfoPanel.panel";

let appInfoPanel;
let serverInfoPanel;
Given('one instance of AppInfoPanel', function () {
    console.log('[GIVEN] one instance of AppInfoPanel');
    appInfoPanel = new AppInfoPanel();
});
Given('one instance of ServerInfoPanel', function () {
    console.log('[GIVEN] one instance of ServerInfoPanel');
    serverInfoPanel = new ServerInfoPanel();
});
