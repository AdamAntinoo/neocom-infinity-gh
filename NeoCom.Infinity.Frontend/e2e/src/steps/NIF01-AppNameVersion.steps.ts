// - CUCUMBER
import { Before } from 'cucumber';
import { Given } from 'cucumber';
import { Then } from 'cucumber';
import { When } from 'cucumber';
// - ASSERTION
import { expect } from 'chai';
import { assert } from 'chai';
import { browser } from 'protractor';
// - PAGES
import { LoginValidationPage } from '../pages/LoginValidation.page';
import { IsolationService } from '../support/IsolationService.support';

let loginValidationPage: LoginValidationPage;
let isolationService: IsolationService;
// let worldPanel: any;

Before(() => {
  loginValidationPage = new LoginValidationPage();
  isolationService = new IsolationService();
});

Then('panel displays the next fields', function (dataTable) {
  dataTable.rows().forEach((row) => {
    switch (row[0]) {
      case 'app-name':
        assert.equal(row[1], isolationService.getAppName());
        break;
    }
  });
});

// Given('I am on the login landing page', async () => {
//   await page.navigateTo('-ANY-CODE-IS-VALID-');
// });
// When('I do nothing', () => { });
// Then('I should see a {string} panel', function (panelColor: string) {
//   // Write code here that turns the phrase above into concrete actions
//   return 'completed';
// });
// Then('The panel should render the application name', async () => {
//   browser.debugger();
//   expect(await page.getAppName()).to.equal('NeoCom');
// });
// Then('The panel should render the application version', async () => {
//   expect(await page.getAppVersion()).to.equal('v0.16.1');
// });
// Then('The panel should render the copyright notice', async () =>{
//   expect(await page.getCopyright()).to.equal('© 2019,2020 Dimensinfin Industries');
// });
