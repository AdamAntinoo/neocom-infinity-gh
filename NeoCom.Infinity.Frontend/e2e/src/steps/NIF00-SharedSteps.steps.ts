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
// - SUPPORT
import { IsolationService } from '../support/IsolationService.support';
import { SharedFunctionalityPage } from '../pages/SharedFunctionality.page';

let page: SharedFunctionalityPage;
let isolationService: IsolationService;

Before(() => {
    console.log('>THIS IS THE SharedFunctionalityPage BEFORE');
    page = new SharedFunctionalityPage();
    isolationService = new IsolationService();
});
Then('there is a {string}', async (panelIdentifier) => {
    expect(await element(by.id(panelIdentifier))).to.be.ok;
});
