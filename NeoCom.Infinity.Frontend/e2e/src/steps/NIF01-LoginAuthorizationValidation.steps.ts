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
When('the request has the code {string} and state {string}', async (requestCode, requestState) => {
    await loginValidationPage.navigateTo(requestCode, requestState);
    authorizationWorld.setCode(requestCode);
    authorizationWorld.setState(requestState);
    // authorizationWorld.setResponse(loginValidationPage.getResponse());
});
Then('there is a {string}', async (panelIdentifier) => {
    expect(await element(by.id(panelIdentifier))).to.be.ok;
});
// When('there is not a {string}', async (panelIdentifier) => {
//     expect(await element(by.id(panelIdentifier)).getId()).to.be.undefined;
// });
// Then('the response has a valid JWT token', function () {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });
// Then('the response has a Credential', function () {
//     // Write code here that turns the phrase above into concrete actions
//     return 'pending';
// });


Then('appinfo panel displays the next fields', async (dataTable) => {
    const APP_NAME: string = 'app-name';
    const APP_VERSION: string = 'app-version';
    const COPYRIGHT: string = 'copyright';
    console.log('>Then appinfo panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then appinfo panel displays the next fields]> row=' + JSON.stringify(row));
    // dataTable.hashes().forEach((row) => {
    console.log('-[Then appinfo panel displays the next fields]>' + APP_NAME + ': ' +
        isolationService.decodeDataTableRow(row, APP_NAME));
    // assert.equal(isolationService.decodeDataTableRow(row, APP_NAME), isolationService.getAppName());
    expect(await loginValidationPage.getAppName()).to.equal(isolationService.decodeDataTableRow(row, APP_NAME));
    console.log('-[Then appinfo panel displays the next fields]>' + APP_VERSION + ': ' +
        isolationService.decodeDataTableRow(row, APP_VERSION));
    assert.equal(isolationService.decodeDataTableRow(row, APP_VERSION), isolationService.getAppVersion());
    console.log('-[Then appinfo panel displays the next fields]>' + COPYRIGHT + ': ' +
        isolationService.decodeDataTableRow(row, COPYRIGHT));
    assert.equal(isolationService.decodeDataTableRow(row, COPYRIGHT), isolationService.getCopyright());
    // });
    console.log('<Then panel displays the next fields');
});
Then('loginvalidationprogress panel displays the next fields', async (dataTable) => {
    const PROGRESS_LABEL: string = 'progress-label';
    const PROGRESS_CODE: string = 'progress-code';
    console.log('>Then loginvalidationprogress panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then loginvalidationprogress panel displays the next fields]> row=' + JSON.stringify(row));
    // dataTable.hashes().forEach((row) => {
    // console.log('-[Then loginvalidationprogress panel displays the next fields]>' + PROGRESS_LABEL + ': ' +
    //         isolationService.decodeDataTableRow(row, PROGRESS_LABEL));
    // assert.equal(isolationService.decodeDataTableRow(row, APP_NAME), isolationService.getAppName());
    expect(await loginValidationPage.getProgressLabel()).to.equal(isolationService.decodeDataTableRow(row, PROGRESS_LABEL));
    expect(await loginValidationPage.getProgressCode()).to.equal(isolationService.decodeDataTableRow(row, PROGRESS_CODE));
    // console.log('-[Then panel displays the next fields]>' + APP_VERSION + ': ' +
    //     isolationService.decodeDataTableRow(row, APP_VERSION));
    // assert.equal(isolationService.decodeDataTableRow(row, APP_VERSION), isolationService.getAppVersion());
    // console.log('-[Then panel displays the next fields]>' + COPYRIGHT + ': ' +
    //     isolationService.decodeDataTableRow(row, COPYRIGHT));
    // assert.equal(isolationService.decodeDataTableRow(row, COPYRIGHT), isolationService.getCopyright());
    // });
    console.log('<Then loginvalidationprogress panel displays the next fields');
});
//    ?And appinfo panel displays the next fields
//     | app - name | app - version | copyright |
//        | NeoCom.Infinity | 0.16.0 - TEST | <environment.copyright> |
//     Undefined.Implement with the following snippet:

//    ?And The response has a valid JWT token # e2e / src / steps / NIF01 - LoginAuthorizationValidation.steps.ts: 84
// Pending
//     ? And the JWT token has the next contents
//         | accountName | uniqueId |
//        | Adam Antinoo | tranquility / 123 |
//     Undefined.Implement with the following snippet:

When('the JWT token has the next contents', function (dataTable) {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
});

//    ?And the Credential has the next contents
//     | accountId | accountName | uniqueId |
//        | 123 | Adam Antinoo | tranquility / 123 |
//     Undefined.Implement with the following snippet:

When('the Credential has the next contents', function (dataTable) {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
});

//    ?And after the validations and process
// Undefined.Implement with the following snippet:

When('after the validations and process', function () {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
});

//    ?Then the page jumps to the "Dashboard Page" page
// Undefined.Implement with the following snippet:

Then('the page jumps to the {string} page', function (string) {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
});
