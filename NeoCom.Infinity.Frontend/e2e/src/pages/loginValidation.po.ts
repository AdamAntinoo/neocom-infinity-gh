import { browser, by, element } from 'protractor';

export class LoginValidationPage {
  navigateTo() {
    return browser.get(browser.baseUrl) as Promise<any>;
  }

  getTitleText() {
    return element(by.css('app-root .content span')).getText() as Promise<string>;
  }
  getAppName() {
    return element(by.css('.app-name')).getText() as Promise<string>;
  }
  getAppVersion() {
    return element(by.css('app-info-panel span .app-version')).getText() as Promise<string>;
  }
  getCopyright() {
    return element(by.css('app-info-panel span .app-copyright')).getText() as Promise<string>;
  }
}
