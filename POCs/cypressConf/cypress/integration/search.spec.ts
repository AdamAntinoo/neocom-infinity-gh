// cypress/google/search.spec.ts
describe('When I visit Google', () => {
    beforeEach(() => {
        cy.visit('https://google.com/imghp')
    });

    it('I should be able to search', () => {
        cy.searchGoogle('gatos pictures')
        // cy.get('input[title="Buscar"]')
        //     .type('cat pictures{enter}');
    })
});
