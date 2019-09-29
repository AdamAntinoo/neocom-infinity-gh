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
import { IsolationService } from '../support/IsolationService.support';
import { AuthorizationWorld } from '../support/AuthorizationWorld.world';
// - DEFINITIONS
const APP_NAME: string = 'app-name';
const APP_VERSION: string = 'app-version';
const COPYRIGHT: string = 'copyright';
const INVALID_STATE: string = '-INVALID-STATE-';
const VALID_STATE: string = 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct';

let loginValidationPage: LoginValidationPage;
let isolationService: IsolationService;
let authorizationWorld: AuthorizationWorld;

Before(() => {
    loginValidationPage = new LoginValidationPage();
    isolationService = new IsolationService();
    authorizationWorld = new AuthorizationWorld();
    authorizationWorld.setPage(loginValidationPage);
});

Given('I am on the {string} page', function (pageIdentifier) {
    assert.equal(loginValidationPage.getPageName(), pageIdentifier, 'Check the target page identifier.');
});
When('the request has the code {string} and state {string}', function (requestCode, requestState) {
    loginValidationPage.navigateTo(requestCode, requestState);
    authorizationWorld.setCode(requestCode);
    authorizationWorld.setState(requestState);
});

// When('the request has the code {string}', function (requestCode) {
// });
// When('the request has the state {string}', function (requestState) {
//     loginValidationPage.navigateTo(requestCode, INVALID_STATE);
//     authorizationWorld.setCode(requestCode);
// });
Then('the page shows {int} panels', function (panelCount) {
    assert.equal(loginValidationPage.getPanelCount(), panelCount, 'Check the number of panels visible on the target page');
});
Then('there is a {string}', function (panelIdentifier) {
    assert.isOk(loginValidationPage.getPagePanelById(panelIdentifier), 'And there is a ' + panelIdentifier);
});

Then('panel displays the next fields', function (dataTable) {
    console.log('>Then panel displays the next fields');
    dataTable.hashes().forEach((row) => {
        console.log('-[Then panel displays the next fields]>' + APP_NAME + ': ' +
            isolationService.decodeDataTableRow(row, APP_NAME));
        assert.equal(isolationService.decodeDataTableRow(row, APP_NAME), isolationService.getAppName());
        console.log('-[Then panel displays the next fields]>' + APP_VERSION + ': ' +
            isolationService.decodeDataTableRow(row, APP_VERSION));
        assert.equal(isolationService.decodeDataTableRow(row, APP_VERSION), isolationService.getAppVersion());
        console.log('-[Then panel displays the next fields]>' + COPYRIGHT + ': ' +
            isolationService.decodeDataTableRow(row, COPYRIGHT));
        assert.equal(isolationService.decodeDataTableRow(row, COPYRIGHT), isolationService.getCopyright());
    });
    console.log('<Then panel displays the next fields');
});
