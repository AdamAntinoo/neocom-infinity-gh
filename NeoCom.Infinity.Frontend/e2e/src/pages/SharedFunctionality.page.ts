// - PROTRACTOR
import { browser } from 'protractor';
import { by } from 'protractor';
import { element } from 'protractor';

export class SharedFunctionalityPage {
    private pageName: string;

    public getPageName(): string {
        return this.pageName as string;
    }
    public setPageName(newName: string): string{
        this.pageName = newName;
        return this.pageName;
    }

    public getPagePanelById(panelIdentifier: string): any {
        console.log('>[SharedFunctionalityPage.getPagePanelById]>Searching for panel: ' + panelIdentifier);
        return element(by.id(panelIdentifier));
    }
    // - APP INFO PANEL
    public getAppName(): Promise<string> {
        return element(by.css('.app-name')).getText() as Promise<string>;
    }
    public getAppVersion(): Promise<string> {
        return element(by.css('.app-version')).getText() as Promise<string>;
    }
    public getCopyright(): Promise<string> {
        return element(by.css('.app-copyright')).getText() as Promise<string>;
    }
}
