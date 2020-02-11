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
    // cy.visit(urlRequest);
});
Then('there is a tab-container-panel with {int} tabs', function (int) {
    console.log('[THEN] there is a tab-container-panel with {int} tabs');
    cy.get('tab-container-panel').find('li').should('have.length', 2);
});
Then('there is no tab selected', function () {
    console.log('[THEN] there is no tab selected');
    cy.get('tab-container-panel').find('.active').should('have.length', 0);
});
