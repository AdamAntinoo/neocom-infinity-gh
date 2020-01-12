// - CUCUMBER
import { Before } from 'cucumber';
import { Given } from 'cucumber';
import { Then } from 'cucumber';
import { When } from 'cucumber';
// - PROTRACTOR
import { by } from 'protractor';
import { element } from 'protractor';
import { browser } from 'protractor';
// - ASSERTION
import { assert } from 'chai';
import { expect } from 'chai';
// - PAGES
import { DashboardHomePage } from '../pages/DashboardHome.page';
import { IsolationService } from '../support/IsolationService.support';
import { AppInfoPanel } from '../pages/AppInfoPanel.panel';
import { ServerInfoPanel } from '../pages/ServerInfoPanel.panel';
// - SERVICES
import { neocom_constants } from '../support/neocom-constants.platform';
import { Credential } from '../../../src/app/domain/Credential.domain';

// - DEFINITIONS
let isolationService: IsolationService;
let appInfoPanel: AppInfoPanel;
let serverInfoPanel: ServerInfoPanel;
let dashboardPage: DashboardHomePage;

Before(() => {
    console.log('>THIS IS NIF02-DashboardHome BEFORE');
    isolationService = new IsolationService();
});

Given('one instance of AppInfoPanel', function () {
    console.log('[GIVEN] one instance of AppInfoPanel');
    appInfoPanel = new AppInfoPanel();
});
Given('one instance of ServerInfoPanel', function () {
    console.log('[GIVEN] one instance of ServerInfoPanel');
    serverInfoPanel = new ServerInfoPanel();
});
Given('the next authentication token', async (dataTable) => {
    console.log('[GIVEN] the next authentication token');
    browser.waitForAngularEnabled(true);
    await browser.get(browser.baseUrl + 'exceptionInfo?waiting=AuthenticationToken') as Promise<any>;
    browser.driver.sleep(2000);
    browser.waitForAngular();
    const JWT_TOKEN: string = 'jwtToken';
    let jwtToken = isolationService.decodeDataTableRow(dataTable.hashes()[0], JWT_TOKEN);
    isolationService.setToSession(neocom_constants.JWTTOKEN_KEY, jwtToken);
    browser.driver.sleep(2000);
    browser.waitForAngular();
});
Given('a valid credential with the next data', async (dataTable) => {
    console.log('[GIVEN] a valid credential with the next data');
    const UNIQUE_ID: string = 'uniqueId';
    const ACCOUNT_ID: string = 'accountId';
    const ACCOUNT_NAME: string = 'accountName';
    const CORPORATION_ID: string = 'corporationId';
    await browser.get(browser.baseUrl + 'exceptionInfo?waiting=Credential') as Promise<any>;
    browser.driver.sleep(1000);
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
Given('one Dashboard Home Page', async () => {
    console.log('[GIVEN] one Dashboard Home Page');
    dashboardPage = new DashboardHomePage();
    assert.equal(dashboardPage.getPageName(), 'Dashboard Home Page', 'Check the target page identifier.');
});
When('the page is activated with the request id {string}', async (string) => {
    console.log('[WHEN] the page is activated with the request id { string }');
    const urlRequest = 'dashboard';
    console.log('>[DashboardHomePage.navigateTo]> Navigating to page: ' + urlRequest);
    // browser.driver.sleep(3000);
    await browser.get(browser.baseUrl + urlRequest) as Promise<any>;
    browser.driver.sleep(3000); // Reduced after success
    browser.waitForAngular();
    browser.waitForAngular();
});
Then('there is a {string} with the next fields', async (panelType, dataTable) => {
    console.log('[THEN] there is a {string} with the next fields');
    const row = dataTable.hashes()[0];
    switch (panelType) {
        case 'appinfo-panel':
            await appInfoPanel.validateAppInfoPanel(row);
            break;
        case 'serverinfo-panel':
            browser.driver.sleep(1000);
            await serverInfoPanel.validateServerInfoPanel(row);
            break;
    }
});
Then('there is a {string} with corporation contents', async (panelName, dataTable) => {
    const CORPORATION_HEADER: string = 'corporation-header';
    const CORPORATION_CEO_HEADER: string = 'corporation-ceo-header';
    const ALLIANCE_HEADER: string = 'alliance-header';
    console.log('[THEN] there is a {string} with corporation contents');
    browser.driver.sleep(4000);
    browser.waitForAngular();
    const row = dataTable.hashes()[0];
    let corporationHeader = isolationService.decodeDataTableRow(row, CORPORATION_HEADER).toUpperCase();
    console.log('[THEN] there is a {string} with corporation contents> CORPORATION_HEADER=' + corporationHeader);
    expect(await element(by.css('#corporation-header')).getText()).to.equal(corporationHeader);
});

//    ?And there is a "corporation-render" with variant "-HEADER-" with the next fields
//     | corporation - name | corporation - id | corporation - ticker | corporation - members - count |
//        | Planet - Express | [#1427661573] | [PLAM] | 8 MEMBERS |
//     Undefined.Implement with the following snippet:

Then('there is a {string} with variant {string} with the next fields', function (string, string2, dataTable) {
    // Write code here that turns the phrase above into concrete actions
    return 'pending';
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
