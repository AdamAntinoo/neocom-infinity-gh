// - CUCUMBER
import { Before } from 'cucumber';
import { Given } from 'cucumber';
import { Then } from 'cucumber';
import { When } from 'cucumber';
// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';
// - ASSERTION
import { expect } from 'chai';
import { assert } from 'chai';
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
Then('there is a {string}', async (panelIdentifier) => {
    // console.log('>Then there is a {string}' + '=' + JSON.stringify(element(by.id(panelIdentifier)).isPresent()));
    // console.log('>Then there is a {string}' + '=' + JSON.stringify(expect(element(by.id(panelIdentifier)).isPresent())));
    expect(await element(by.id(panelIdentifier))).to.be.ok;
});
When('there is not a {string}', async (panelIdentifier) => {
    expect(await element(by.id(panelIdentifier))).to.be.ok;
});

Then('panel displays the next fields',  (dataTable) => {
    console.log('>Then panel displays the next fields');
    dataTable.hashes().forEach(async (row) => {
        console.log('-[Then panel displays the next fields]>' + APP_NAME + ': ' +
            isolationService.decodeDataTableRow(row, APP_NAME));
        assert.equal(isolationService.decodeDataTableRow(row, APP_NAME), isolationService.getAppName());
        expect(await loginValidationPage.getAppName()).to.equal(isolationService.decodeDataTableRow(row, APP_NAME));
        console.log('-[Then panel displays the next fields]>' + APP_VERSION + ': ' +
            isolationService.decodeDataTableRow(row, APP_VERSION));
        assert.equal(isolationService.decodeDataTableRow(row, APP_VERSION), isolationService.getAppVersion());
        console.log('-[Then panel displays the next fields]>' + COPYRIGHT + ': ' +
            isolationService.decodeDataTableRow(row, COPYRIGHT));
        assert.equal(isolationService.decodeDataTableRow(row, COPYRIGHT), isolationService.getCopyright());
    });
    console.log('<Then panel displays the next fields');
});
