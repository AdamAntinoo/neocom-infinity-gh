// - PROTRACTOR
import { browser } from 'protractor';
import { logging } from 'protractor';
// - COMPONENTS
import { LoginValidationPage } from '../pages/LoginValidation.page';

describe('workspace-project App', () => {
  let page: LoginValidationPage;
  const pageIdentifier = 'Login Validation Page';

  beforeEach(() => {
    page = new LoginValidationPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getPageName()).toEqual(pageIdentifier);
    // await loginValidationPage.navigateTo();
    // expect(loginValidationPage.getPageName()).toBe(pageIdentifier);
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
