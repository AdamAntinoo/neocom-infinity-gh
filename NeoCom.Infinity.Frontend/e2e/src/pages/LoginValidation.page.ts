import { browser, by, element } from 'protractor';
import { Panel } from '../support/panel';

export class LoginValidationPage {
  private PAGE_NAME = 'Login Validation Page';
  private appinfo: any;
  private loginvalidationprogress: any;
  private loginvalidationexception: any;

  public navigateTo(code: string) {
    return browser.get(browser.baseUrl + '/loginValidation?code=' + code) as Promise<any>;
  }

  public getPageName(): string {
    return this.PAGE_NAME as string;
  }
  public getPanelCount(): number {
    let panelCount = 0;
    this.appinfo = element(by.id('appinfo-panel'));
    if (null != this.appinfo) panelCount++;
    this.loginvalidationprogress = element(by.id('loginvalidationprogress-panel'));
    if (null != this.loginvalidationprogress) panelCount++;
    this.loginvalidationexception = element(by.id('loginvalidationexception-panel'));
    if (null != this.loginvalidationexception) panelCount++;
    return panelCount;
  }
  public getPagePanelById(panelIdentifier: string): any {
    switch (panelIdentifier.toLowerCase()) {
      case 'appinfo-panel':
        return this.appinfo;
        break;
      case 'loginvalidationprogress-panel':
        return this.loginvalidationprogress;
        break;
      case 'loginvalidationexception-panel':
        return this.loginvalidationexception;
        break;
    }
  }
  // public getAppInfoPanelType(): string {
  //   let panel = element(by.css('.app-name')).getText();
  //   if (null != panel) return panel.type;
  //   else return 'undefined';
  // }
  // public search4PanelType(panelType: string): Panel {
  //   const panels = element(by.css('.app-name')).all;
  //   for (let panel: any of panels.) {
  //     if (panel.type === panelType) {
  //       return new Panel("Data");
  //     }
  //   }
  //   return null;
  // }
  getTitleText() {
    return element(by.css('app-root .content span')).getText() as Promise<string>;
  }
  getAppName() {
    return element(by.css('.app-name')).getText() as Promise<string>;
  }
  getAppVersion() {
    return element(by.css('.app-version')).getText() as Promise<string>;
  }
  getCopyright() {
    return element(by.css('.app-copyright')).getText() as Promise<string>;
  }
}
