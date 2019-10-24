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
// - DEFINITIONS

let dashboardPage: DashboardHomePage;
let isolationService: IsolationService;
let world: DashboardWorld;

Before(() => {
    console.log('>THIS IS THE DashboardHomePage BEFORE');
    dashboardPage = new DashboardHomePage();
    isolationService = new IsolationService();
    world = new DashboardWorld();
    world.setPage(dashboardPage);
});
/** This state code the preparation phase before going to the page. For example setup before failure generation. */
Given('I am on the Dashboard Home Page page', function () {
    assert.equal(dashboardPage.getPageName(), 'Dashboard Home Page', 'Check the target page identifier.');
});
When('the page is activated', async () => {
    await dashboardPage.navigateTo();
});
Then('Dashboard Home Page appinfo-panel displays the next fields', async (dataTable) => {
    const APP_NAME: string = 'app-name';
    const APP_VERSION: string = 'app-version';
    const COPYRIGHT: string = 'copyright';
    console.log('>Then Dashboard Home Page appinfo-panel panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]> row=' + JSON.stringify(row));
    console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]>' + APP_NAME + ': ' +
        isolationService.decodeDataTableRow(row, APP_NAME));
    expect(await dashboardPage.getAppName()).to.equal(isolationService.decodeDataTableRow(row, APP_NAME).toUpperCase());
    console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]>' + APP_VERSION + ': ' +
        isolationService.decodeDataTableRow(row, APP_VERSION));
    expect(await dashboardPage.getAppVersion()).to.equal(isolationService.decodeDataTableRow(row, APP_VERSION));
    console.log('-[Then Dashboard Home Page appinfo-panel panel displays the next fields]>' + COPYRIGHT + ': ' +
        isolationService.decodeDataTableRow(row, COPYRIGHT));
    expect(await dashboardPage.getCopyright()).to.equal(isolationService.decodeDataTableRow(row, COPYRIGHT));
    console.log('<Then Dashboard Home Page appinfo-panel panel displays the next fields');
});
Then('Dashboard Home Page serverinfo-panel displays the next fields', async (dataTable) => {
    const SERVER_NAME: string = 'server-name';
    const SERVER_STATUS: string = 'server-status';
    const CAPSULEERS: string = 'capsuleers';
    const SERVER_LAST_START = 'server-laststart';
    console.log('>Then Dashboard Home Page serverinfo-panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]> row=' + JSON.stringify(row));
    console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + SERVER_NAME + ': ' +
        isolationService.decodeDataTableRow(row, SERVER_NAME));
    expect(await dashboardPage.getServerName()).to.equal(isolationService.decodeDataTableRow(row, SERVER_NAME));
    console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + SERVER_STATUS + ': ' +
        isolationService.decodeDataTableRow(row, SERVER_STATUS));
    expect(await dashboardPage.getServerStatus()).to.equal(isolationService.decodeDataTableRow(row, SERVER_STATUS));
    console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + CAPSULEERS + ': ' +
        isolationService.decodeDataTableRow(row, CAPSULEERS));
    expect(await dashboardPage.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, CAPSULEERS));
    console.log('-[Then Dashboard Home Page serverinfo-panel displays the next fields]>' + SERVER_LAST_START + ': ' +
        isolationService.decodeDataTableRow(row, SERVER_LAST_START));
    expect(await dashboardPage.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, SERVER_LAST_START));
    console.log('<Then Dashboard Home Page serverinfo-panel displays the next fields');
});
Then('Dashboard Home Page corporationdata-panel displays the next fields', async (dataTable) => {
    const CORPORATION_NAME: string = 'corporation-name';
    const CORPORATION_ID: string = 'corporation-id';
    const CORPORATION_TICKER: string = 'corporation-ticker';
    const CORPORATION_MEMBERS_COUNT = 'corporation-members-count';
    console.log('>Then Dashboard Home Page corporationdata-panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then Dashboard Home Page corporationdata-panel displays the next fields]> row=' + JSON.stringify(row));
    console.log('-[Then Dashboard Home Page corporationdata-panel displays the next fields]>' + CORPORATION_NAME + ': ' +
        isolationService.decodeDataTableRow(row, CORPORATION_NAME));
    expect(await dashboardPage.getCorporationName()).to.equal(isolationService.decodeDataTableRow(row, CORPORATION_NAME));
    // console.log('-[Then serverinfo panel displays the next fields]>' + SERVER_STATUS + ': ' +
    //     isolationService.decodeDataTableRow(row, SERVER_STATUS));
    // expect(await page.getServerStatus()).to.equal(isolationService.decodeDataTableRow(row, SERVER_STATUS));
    // console.log('-[Then serverinfo panel displays the next fields]>' + CAPSULEERS + ': ' +
    //     isolationService.decodeDataTableRow(row, CAPSULEERS));
    // expect(await page.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, CAPSULEERS));
    // console.log('-[Then serverinfo panel displays the next fields]>' + SERVER_LAST_START + ': ' +
    //     isolationService.decodeDataTableRow(row, SERVER_LAST_START));
    // expect(await page.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, SERVER_LAST_START));
    console.log('<Then corporationdata panel displays the next fields');
});
