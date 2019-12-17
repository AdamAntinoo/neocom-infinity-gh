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
import { DashboardHomePage } from '../pages/DashboardHome.page';
import { IsolationService } from '../support/IsolationService.support';
import { DashboardWorld } from '../support/DashboardWorld.world';
import { AppInfoPanel } from '../pages/AppInfoPanel.panel';
// - SERVICES
import { neocom_constants } from '../support/neocom-constants.platform';
import { Credential } from '../../../src/app/domain/Credential.domain';

// - DEFINITIONS
let isolationService: IsolationService;
let appInfoPanel: AppInfoPanel;
let dashboardPage: DashboardHomePage;

// let page: SharedPanelsElements;

Before(() => {
    console.log('>THIS IS NIF02-DashboardHome BEFORE');
    isolationService = new IsolationService();
});

Given('one instance of AppInfoPanel', function () {
    console.log('[GIVEN] one instance of AppInfoPanel');
    appInfoPanel = new AppInfoPanel();
});
Given('the next authentication token', async (dataTable) => {
    console.log('[GIVEN] the next authentication token');
    browser.waitForAngularEnabled(false);
    await browser.get(browser.baseUrl + 'exceptionInfo?waiting=AuthenticationToken') as Promise<any>;
    browser.driver.sleep(4000);
    browser.waitForAngular();
    const JWT_TOKEN: string = 'jwtToken';
    let jwtToken = isolationService.decodeDataTableRow(dataTable.hashes()[0], JWT_TOKEN);
    isolationService.setToSession(neocom_constants.JWTTOKEN_KEY, jwtToken);
});
Given('a valid credential with the next data', async (dataTable) => {
    console.log('[GIVEN] a valid credential with the next data');
    const UNIQUE_ID: string = 'uniqueId';
    const ACCOUNT_ID: string = 'accountId';
    const ACCOUNT_NAME: string = 'accountName';
    const CORPORATION_ID: string = 'corporationId';
    await browser.get(browser.baseUrl + 'exceptionInfo?waiting=Credential') as Promise<any>;
    browser.driver.sleep(2000);
    browser.waitForAngular();
    let row = dataTable.hashes()[0];
    let credential = new Credential({
        "jsonClass": "Credential",
        "uniqueId": isolationService.decodeDataTableRow(row, UNIQUE_ID),
        "accountId": isolationService.decodeDataTableRow(row, ACCOUNT_ID),
        "accountName": isolationService.decodeDataTableRow(row, ACCOUNT_NAME),
        "dataSource": "tranquility",
        "corporationId": isolationService.decodeDataTableRow(row, CORPORATION_ID),
        "assetsCount": 100,
        "walletBalance": 654987.0,
        "miningResourcesEstimatedValue": 345234.0,
        "raceName": "Minmatar"
    });
    isolationService.setToSession(neocom_constants.CREDENTIAL_KEY, JSON.stringify(credential));
});


//    private storeJWT(jwtToken: string): boolean {
//     this.isolationService.setToSession(neocom_constants.JWTTOKEN_EXPIRATION_TIME_KEY,
//         addMinutes(Date.now(), 120));
//     return true;


Given('one Dashboard Home Page', async () => {
    console.log('[GIVEN] one Dashboard Home Page');
    dashboardPage = new DashboardHomePage();
    assert.equal(dashboardPage.getPageName(), 'Dashboard Home Page', 'Check the target page identifier.');
});

When('the page is activated with the request id {string}', async (string) => {
    console.log('[WHEN] The page is activated with the request id { string }');
    // TODO The string parameter contains the code received on the page.
    browser.waitForAngularEnabled(false);
    // browser.waitForAngular();
    // browser.driver.sleep(10000);
    // await dashboardPage.navigateTo();
    const urlRequest = 'dashboard';
    console.log('>[DashboardHomePage.navigateTo]> Navigating to page: ' + urlRequest);
    // browser.driver.sleep(3000);
    await browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    browser.driver.sleep(5000);
    browser.waitForAngular();
});

//    ?Then there is a "appinfo-panel" with the next fields
//     | AppName | AppVersion | AppCopyright |
//        | NEOCOM | v0.16.1 | © 2019, 2020 Dimensinfin Industries |
//     Undefined.Implement with the following snippet:

Then('there is a {string} with the next fields', async (panelType, dataTable) => {
    console.log('[THEN] There is a {string} with the next fields');
    const row = dataTable.hashes()[0];
    switch (panelType) {
        case 'appinfo-panel':
            appInfoPanel.validateAppInfoPanel(row);
            break;
    }
});

// Then('Dashboard Home Page appinfo-panel displays the next fields', async (dataTable) => {
//     const APP_NAME: string = 'app-name';
//     const APP_VERSION: string = 'app-version';
//     const COPYRIGHT: string = 'copyright';
//     console.log('>Then Dashboard Home Page appinfo-panel panel displays the next fields');
//     const row = dataTable.hashes()[0];
//     console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]> row=' + JSON.stringify(row));
//     console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]>' + APP_NAME + ': ' +
//         isolationService.decodeDataTableRow(row, APP_NAME));
//     expect(await dashboardPage.getAppName()).to.equal(isolationService.decodeDataTableRow(row, APP_NAME).toUpperCase());
//     console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]>' + APP_VERSION + ': ' +
//         isolationService.decodeDataTableRow(row, APP_VERSION));
//     expect(await dashboardPage.getAppVersion()).to.equal(isolationService.decodeDataTableRow(row, APP_VERSION));
//     console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]>' + COPYRIGHT + ': ' +
//         isolationService.decodeDataTableRow(row, COPYRIGHT));
//     expect(await dashboardPage.getCopyright()).to.equal(isolationService.decodeDataTableRow(row, COPYRIGHT));
//     console.log('<Then Dashboard Home Page appinfo-panel panel displays the next fields');
// });
// Then('Dashboard Home Page serverinfo-panel displays the next fields', async (dataTable) => {
//     const SERVER_NAME: string = 'server-name';
//     const SERVER_STATUS: string = 'server-status';
//     const CAPSULEERS: string = 'capsuleers';
//     const SERVER_LAST_START = 'server-laststart';
//     console.log('>Then Dashboard Home Page serverinfo-panel displays the next fields');
//     const row = dataTable.hashes()[0];
//     console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]> row=' + JSON.stringify(row));
//     console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + SERVER_NAME + ': ' +
//         isolationService.decodeDataTableRow(row, SERVER_NAME));
//     expect(await dashboardPage.getServerName()).to.equal(isolationService.decodeDataTableRow(row, SERVER_NAME));
//     console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + SERVER_STATUS + ': ' +
//         isolationService.decodeDataTableRow(row, SERVER_STATUS));
//     // expect(await dashboardPage.getServerStatus()).to.equal(isolationService.decodeDataTableRow(row, SERVER_STATUS));
//     // console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + CAPSULEERS + ': ' +
//     //     isolationService.decodeDataTableRow(row, CAPSULEERS));
//     // expect(await dashboardPage.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, CAPSULEERS));
//     // console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + SERVER_LAST_START + ': ' +
//     //     isolationService.decodeDataTableRow(row, SERVER_LAST_START));
//     // expect(await dashboardPage.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, SERVER_LAST_START));
//     // console.log('<Then Dashboard Home Page serverinfo-panel displays the next fields');
// });
// Then('Dashboard Home Page corporationdata-panel displays the next fields', async (dataTable) => {
//     const CORPORATION_NAME: string = 'corporation-name';
//     const CORPORATION_ID: string = 'corporation-id';
//     const CORPORATION_TICKER: string = 'corporation-ticker';
//     const CORPORATION_MEMBERS_COUNT = 'corporation-members-count';
//     console.log('>Then Dashboard Home Page corporationdata-panel displays the next fields');
//     const row = dataTable.hashes()[0];
//     console.log('-[Then Dashboard Home Page corporationdata-panel displays the next fields]> row=' + JSON.stringify(row));
//     console.log('-[Then Dashboard Home Page corporationdata-panel displays the next fields]>' + CORPORATION_NAME + ': ' +
//         isolationService.decodeDataTableRow(row, CORPORATION_NAME));
//     // expect(await dashboardPage.getCorporationName()).to.equal(isolationService.decodeDataTableRow(row, CORPORATION_NAME));
//     // console.log('-[Then serverinfo panel displays the next fields]>' + SERVER_STATUS + ': ' +
//     //     isolationService.decodeDataTableRow(row, SERVER_STATUS));
//     // expect(await page.getServerStatus()).to.equal(isolationService.decodeDataTableRow(row, SERVER_STATUS));
//     // console.log('-[Then serverinfo panel displays the next fields]>' + CAPSULEERS + ': ' +
//     //     isolationService.decodeDataTableRow(row, CAPSULEERS));
//     // expect(await page.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, CAPSULEERS));
//     // console.log('-[Then serverinfo panel displays the next fields]>' + SERVER_LAST_START + ': ' +
//     //     isolationService.decodeDataTableRow(row, SERVER_LAST_START));
//     // expect(await page.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, SERVER_LAST_START));
//     console.log('<Then corporationdata panel displays the next fields');
// });
