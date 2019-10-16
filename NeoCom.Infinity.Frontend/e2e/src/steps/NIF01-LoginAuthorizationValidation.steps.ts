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

let page: LoginValidationPage;
let isolationService: IsolationService;
let authorizationWorld: AuthorizationWorld;

Before(() => {
    // Create the conponent by manual injection.
    // const LOGIN_VALIDATION_PAGE_TOKEN = new InjectionToken<LoginValidationPage>('Manually constructed LoginValidationPage', {
    //     providedIn: 'root',
    //     factory: () => new LoginValidationPage(inject(WebStorageService), inject(WebStorageService)),
    // });

    // const instance = injector.get(LOGIN_VALIDATION_PAGE_TOKEN);

    // loginValidationPage = new LoginValidationPage(inject(WebStorageService), inject(WebStorageService));
    page = new LoginValidationPage();
    isolationService = new IsolationService();
    authorizationWorld = new AuthorizationWorld();
    authorizationWorld.setPage(page);
});

Given('I am on the {string} page', function (pageIdentifier) {
    assert.equal(page.getPageName(), pageIdentifier, 'Check the target page identifier.');
});
When('the request has the code {string} and state {string}', async (requestCode, requestState) => {
    await page.navigateTo(requestCode, requestState);
    authorizationWorld.setCode(requestCode);
    authorizationWorld.setState(requestState);
    // authorizationWorld.setResponse(loginValidationPage.getResponse());
});
Then('there is a {string}', async (panelIdentifier) => {
    expect(await element(by.id(panelIdentifier))).to.be.ok;
});
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
    expect(await page.getAppName()).to.equal(isolationService.decodeDataTableRow(row, APP_NAME));
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
    expect(await page.getProgressLabel()).to.equal(isolationService.decodeDataTableRow(row, PROGRESS_LABEL));
    expect(await page.getProgressCode()).to.equal(isolationService.decodeDataTableRow(row, PROGRESS_CODE));
    console.log('<Then loginvalidationprogress panel displays the next fields');
});
Then('loginvalidationexception panel displays the next fields', async (dataTable) =>{
    console.log('>[Then loginvalidationexception panel displays the next fields]');
    const EXCEPTION_MESSAGE: string = 'exception-message';
    const row = dataTable.hashes()[0];
    console.log('-[Then loginvalidationexception panel displays the next fields]> row=' + JSON.stringify(row));
    expect(await page.getExceptionMessage()).to.equal(isolationService.decodeDataTableRow(row, EXCEPTION_MESSAGE));
    console.log('<[Then loginvalidationexception panel displays the next fields]');
});
