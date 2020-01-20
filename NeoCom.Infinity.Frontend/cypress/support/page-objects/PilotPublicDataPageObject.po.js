const PILOT_HEADER = 'pilot-header';

class PilotPublicDataPageObject {
    // static visit() {
    //     cy.visit('/');
    // }
    static checkPilotHeader(target) {
            cy.get('#pilot-header').contains(target);
        }
        // static pressSearch() {
        //     cy.get(SEARCH_BUTTON).contains(SEARCH_TEXT)
        //         .click();
        //     return new GoogleResultsPage();
        // }
}