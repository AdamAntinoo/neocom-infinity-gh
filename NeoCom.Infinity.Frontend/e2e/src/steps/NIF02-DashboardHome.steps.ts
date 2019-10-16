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
// const INVALID_STATE: string = '-INVALID-STATE-';
// const VALID_STATE: string = 'LU5FT0NPTS5JTkZJTklUWS1ERVZFTE9QTUVOVC1WQUxJRCBTVEFURSBTVFJJTkct';

let page: DashboardHomePage;
let isolationService: IsolationService;
let world: DashboardWorld;

Before(() => {
    page = new DashboardHomePage();
    isolationService = new IsolationService();
    world = new DashboardWorld();
    world.setPage(page);
});
//    ?Given I am on the Dashboard Home Page page
// Undefined.Implement with the following snippet:

Given('I am on the Dashboard Home Page page', function () {
    assert.equal(page.getPageName(), 'Dashboard Home Page', 'Check the target page identifier.');
});
//    ?When the page is activated
// Undefined.Implement with the following snippet:

When('the page is activated', function () {
    // Write code here that turns the phrase above into concrete actions
    // return 'pending';
});
Then('serverinfo panel displays the next fields', async (dataTable) => {
    const SERVER_NAME: string = 'server-name';
    const SERVER_STATUS: string = 'server-status';
    const CAPSULEERS: string = 'capsuleers';
    console.log('>Then serverinfo panel displays the next fields');
    const row = dataTable.hashes()[0];
    console.log('-[Then serverinfo panel displays the next fields]> row=' + JSON.stringify(row));
    // dataTable.hashes().forEach((row) => {
    console.log('-[Then serverinfo panel displays the next fields]>' + SERVER_NAME + ': ' +
        isolationService.decodeDataTableRow(row, SERVER_NAME));
    // assert.equal(isolationService.decodeDataTableRow(row, APP_NAME), isolationService.getAppName());
    expect(await page.getServerName()).to.equal(isolationService.decodeDataTableRow(row, SERVER_NAME));
    console.log('-[Then serverinfo panel displays the next fields]>' + SERVER_STATUS + ': ' +
        isolationService.decodeDataTableRow(row, SERVER_STATUS));
    expect(await page.getServerStatus()).to.equal(isolationService.decodeDataTableRow(row, SERVER_STATUS));
    // assert.equal(isolationService.decodeDataTableRow(row, SERVER_STATUS), isolationService.getServerStatus());
    console.log('-[Then serverinfo panel displays the next fields]>' + CAPSULEERS + ': ' +
        isolationService.decodeDataTableRow(row, CAPSULEERS));
    expect(await page.getCapsuleers()).to.equal(isolationService.decodeDataTableRow(row, CAPSULEERS));
    // assert.equal(isolationService.decodeDataTableRow(row, CAPSULEERS), page.getCapsuleers());
    // });
    console.log('<Then serverinfo panel displays the next fields');
});
