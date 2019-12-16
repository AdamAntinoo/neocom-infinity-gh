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
// - WEBSTORAGE
import { inject } from '@angular/core';
import { InjectionToken } from '@angular/core';
import { WebStorageService } from 'angular-webstorage-service';
// - PAGES
import { LoginValidationPage } from '../pages/LoginValidation.page';
import { IsolationService } from '../support/IsolationService.support';
import { AuthorizationWorld } from '../support/AuthorizationWorld.world';
// - DEFINITIONS
const INVALID_STATE: string = '-INVALID-STATE-';
const VALID_STATE: string = 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct';

let loginPage: LoginValidationPage;
let isolationService: IsolationService;
let authorizationWorld: AuthorizationWorld;

// Before(() => {
//     console.log('>THIS IS THE LoginValidationPage BEFORE');
//     loginPage = new LoginValidationPage();
//     // page = loginPage;
//     isolationService = new IsolationService();
//     authorizationWorld = new AuthorizationWorld();
//     authorizationWorld.setPage(loginPage);
// });

Given('I am on the Login Validation Page page', function () {
    assert.equal(loginPage.getPageName(), 'Login Validation Page', 'Check the target page identifier.');
});
When('the request has the code {string} and state {string}', async (requestCode, requestState) => {
    await loginPage.navigateTo(requestCode, requestState);
    authorizationWorld.setCode(requestCode);
    authorizationWorld.setState(requestState);
    // authorizationWorld.setResponse(loginValidationPage.getResponse());
});
Then('loginvalidationprogress panel displays the next fields', async (dataTable) => {
    const PROGRESS_LABEL: string = 'progress-label';
    const PROGRESS_CODE: string = 'progress-code';
    console.log('>Then loginvalidationprogress panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then loginvalidationprogress panel displays the next fields]> row=' + JSON.stringify(row));
    expect(await loginPage.getProgressLabel()).to.equal(isolationService.decodeDataTableRow(row, PROGRESS_LABEL));
    expect(await loginPage.getProgressCode()).to.equal(isolationService.decodeDataTableRow(row, PROGRESS_CODE));
    console.log('<Then loginvalidationprogress panel displays the next fields');
});
Then('loginvalidationexception panel displays the next fields', async (dataTable) => {
    console.log('>[Then loginvalidationexception panel displays the next fields]');
    const EXCEPTION_MESSAGE: string = 'exception-message';
    const row = dataTable.hashes()[0];
    console.log('-[Then loginvalidationexception panel displays the next fields]> row=' + JSON.stringify(row));
    expect(await loginPage.getExceptionMessage()).to.equal(isolationService.decodeDataTableRow(row, EXCEPTION_MESSAGE));
    console.log('<[Then loginvalidationexception panel displays the next fields]');
});
