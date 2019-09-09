import { AppPageE2E } from './app.e2e-po';
import { browser, logging } from 'protractor';

describe('workspace-project App', () => {
  let page: AppPageE2E;

  beforeEach(() => {
    page = new AppPageE2E();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getTitleText()).toEqual('attdangular app is running!');
  });

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
