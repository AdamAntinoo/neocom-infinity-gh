import { Before, Given, Then, When } from 'cucumber';
import { expect } from 'chai';

import { AppPage } from '../pages/loginValidation.po';

let page: AppPage;

Before(() => {
  page = new AppPage();
});

Given(/^I am on the home page$/, async () => {
  await page.navigateTo();
});

When(/^I do nothing$/, () => { });

Then(/^I should see the title$/, async () => {
  expect(await page.getTitleText()).to.equal('Resources');
});
// ? Given I am on the login landing page
// Undefined. Implement with the following snippet:

Given('I am on the login landing page', () => {
  // Write code here that turns the phrase above into concrete actions
  const i = 45;
  return 'pending' + i;
});

// ? When I do nothing
// Undefined. Implement with the following snippet:

When('I do nothing', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

// ? Then I should see a "white" panel
// Undefined. Implement with the following snippet:

Then('I should see a {string} panel', function (string) {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

// ? And The panel should render the application name
// Undefined. Implement with the following snippet:

Then('The panel should render the application name', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

// ? And The panel should render the application version
// Undefined. Implement with the following snippet:

Then('The panel should render the application version', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});

// ? And The panel should render the copyright notice
// Undefined. Implement with the following snippet:

Then('The panel should render the copyright notice', function () {
  // Write code here that turns the phrase above into concrete actions
  return 'pending';
});
