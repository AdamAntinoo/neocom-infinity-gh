// - CUCUMBER
import { Before } from 'cucumber';
import { Given } from 'cucumber';
import { Then } from 'cucumber';
import { When } from 'cucumber';
// - ASSERTION
import { expect } from 'chai';
import { assert } from 'chai';
import { browser } from 'protractor';
// - PAGES
import { LoginValidationPage } from '../pages/LoginValidation.page';

let loginValidationPage: LoginValidationPage;
let worldPanel: any;

Before(() => {
    loginValidationPage = new LoginValidationPage();
});

// tslint:disable-next-line: only-arrow-functions
Given('I am on the {string} page', function (pageIdentifier) {
    assert.equal(loginValidationPage.getPageName(), pageIdentifier, 'Check the target page identifier.');
});
When('the request has the code {string}', function (requestCode) {
    loginValidationPage.navigateTo(requestCode);
});
Then('the page shows {int} panels', function (panelCount) {
    assert.equal(loginValidationPage.getPanelCount(), panelCount, 'Check the number of panels visible on the target page');
});
Then('there is a {string}', function (panelIdentifier) {
    assert.isOk(loginValidationPage.getPagePanelById(panelIdentifier), 'And there is a ' + panelIdentifier);
});
