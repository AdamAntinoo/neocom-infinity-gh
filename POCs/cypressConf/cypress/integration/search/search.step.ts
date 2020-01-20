// cypress/integration/search/search.spec.ts
import { Given, Then } from 'cypress-cucumber-preprocessor/steps';

Given('I open Google home page', () => {
    cy.visit('/')
});

Then(/^I search for (.*)$/, (text: string) => {
    cy.searchGoogle(text);
});
