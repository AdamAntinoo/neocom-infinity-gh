import { Given, When, Then } from "cypress-cucumber-preprocessor/steps";
// let appNameComponent;

Given('page zero', () => {
    cy.visit('http://neocom.infinity.local/app/loginValidation?code=b__Y5_Btn0ens8j_QviKFQ&state=LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct');
    cy.visit('http://neocom.infinity.local/app/dashboard');
})

When('search application name', () => {
    let appNameComponent = cy.get('app-info-panel')
    console.log('component: ' + JSON.stringify(appNameComponent));
})

Then('the name should contain {string}', (appName) => {
    cy.get('app-info-panel').get('.app-name').contains(appName);
})