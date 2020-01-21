// - CUCUMBER-PREPROCESSOR
import { Given } from "cypress-cucumber-preprocessor/steps";
import { When } from "cypress-cucumber-preprocessor/steps";
import { Then } from "cypress-cucumber-preprocessor/steps";
// - SERVICES
import { IsolationService } from '../../support/IsolationService.support';

When('the DashBoardPage is activated with the request id {string}', function (string) {
    console.log('[WHEN] the page is activated with the request id { string }');
    const urlRequest = '/dashboard';
    console.log('>[DashboardHomePage.navigateTo]> Navigating to page: ' + urlRequest);
    // WARNING. Because the page dependency to stored data I have to go first to the login page.
    // TODO - This is suitable to be converted into a command.
    new IsolationService().doLoginPage();
    cy.visit(urlRequest);
});
