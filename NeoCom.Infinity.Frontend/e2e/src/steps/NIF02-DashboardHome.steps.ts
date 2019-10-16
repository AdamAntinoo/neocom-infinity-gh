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
import { DashboardHomePage } from '@pages/DashboardHome.page';
import { IsolationService } from '@support/IsolationService.support';
import { DashboardWorld } from '@support/DashboardWorld.world';
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
