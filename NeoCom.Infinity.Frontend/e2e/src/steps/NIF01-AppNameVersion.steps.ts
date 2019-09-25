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

// - DEFINITIONS
const APP_NAME: string = 'app-name';
const APP_VERSION: string = 'app-version';
const COPYRIGHT: string = 'copyright';
// const DATATABLE_FIELD_NAME: number = 0;
// const DATATABLE_FIELD_VALUE: number = 1;
let loginValidationPage: LoginValidationPage;
let isolationService: IsolationService;
// let worldPanel: any;

Before(() => {
    loginValidationPage = new LoginValidationPage();
    isolationService = new IsolationService();
});

Then('panel displays the next fields', function (dataTable) {
    console.log('>Then panel displays the next fields');
    dataTable.hashes().forEach((row) => {
        console.log('-' + APP_NAME + ': ' + isolationService.decodeDataTableRow(row, APP_NAME));
        assert.equal(isolationService.decodeDataTableRow(row, APP_NAME), isolationService.getAppName());
        console.log('-' + APP_VERSION + ': ' + isolationService.decodeDataTableRow(row, APP_VERSION));
        assert.equal(isolationService.decodeDataTableRow(row, APP_VERSION), isolationService.getAppVersion());
        console.log('-' + COPYRIGHT + ': ' + isolationService.decodeDataTableRow(row, COPYRIGHT));
        assert.equal(isolationService.decodeDataTableRow(row, COPYRIGHT), isolationService.getCopyright());

        // const appName = ;
        // let value = row[DATATABLE_FIELD_VALUE];
        // value = isolationService.replaceValueTemplated(value);
        // console.log('-Field Name: ' + row[DATATABLE_FIELD_NAME]);
        // console.log('-Field Value: ' + value);
        // switch (row[DATATABLE_FIELD_NAME]) {
        //     case 'app-name':
        //         assert.equal(value, isolationService.getAppName());
        //         break;
        //     case 'app-version':
        //         assert.equal(value, isolationService.getAppVersion());
        //         break;
        //     case 'app-copyright':
        //         assert.equal(value, isolationService.getCopyright());
        //         break;
        // }
    });
    console.log('<Then panel displays the next fields');
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
