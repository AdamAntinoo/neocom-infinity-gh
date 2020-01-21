// - CORE
import { environment } from './environment';

export class IsolationService {
    doLoginPage() {
        let code = Cypress.env('code')
        let state = Cypress.env('state')
        let urlRequest = '/loginValidation?code=' + code +
            '&state=' + state;
        console.log('-[IsolationService.doLoginPage]> urlRequest=' + urlRequest);
        cy.visit(urlRequest);
    }
    // - C U C U M B E R   D E C O D I N G
    public decodeDataTableRow(row: any, columnIdentifier: string): string {
        console.log('-[decodeDataTableRow]>row=' + JSON.stringify(row));
        console.log('-[decodeDataTableRow]>columnIdentifier=' + columnIdentifier);
        let foundValue = row[columnIdentifier];
        console.log('-[decodeDataTableRow]>foundValue=' + foundValue);
        if (null != foundValue) return this.replaceValueTemplated(foundValue)
        else return '-undefined-';
    }
    /**
    * This function replaces values found on Gherkin files by configuration values if they fit the <name> syntax.
    * There are 3 sets of templated values. Environmental that will replace the value by an 'environtment' value,
    * configuration that will do the same with an application configured constant and system that will replace the
    * value by the result of a system function.
    * @param value the value to check for templated.
    */
    public replaceValueTemplated(value: string): string {
        let regex = /^<(.+)\.(.+)>$/g
        if (regex.test(value)) {
            const domain = RegExp.$1;
            const name = RegExp.$2;
            console.log('-[replaceValueTemplated]>domain=' + domain);
            console.log('-[replaceValueTemplated]>name=' + name);
            if (null != domain) {
                switch (domain) {
                    case 'environment':
                        return this.replaceEnvironmentTemplate(name);
                        break;
                    case 'constant':
                        return this.replaceConfigurationTemplate(name);
                        break;
                    case 'system':
                        return this.replaceSystemTemplate(name);
                        break;
                }
            }
        }
        return value;
    }
    public replaceEnvironmentTemplate(templateName: string): string {
        switch (templateName) {
            case 'app-name':
                return environment.appName;
            case 'app-version':
                return environment.appVersion;
            case 'copyright':
                return environment.copyright;
        }
        return '-undefined-';
    }
    public replaceConfigurationTemplate(templateName: string): string {
        return '-undefined-';
    }
    public replaceSystemTemplate(templateName: string): string {
        return '-undefined-';
    }

    // - S T O R A G E   A C C E S S
    public setToSession(key: string, content: any): any {
        console.log('>[IsolationService.setToSession]> ' + key + ': ' + content);
        // console.log("sessionStorage.setItem('" + key + "','" + content + "');");
        // browser.executeScript("sessionStorage.setItem('" + key + "','" + content + "');");
    }
    // public getFromSession(_key: string): any {
    //     return browser.executeScript('sessionStorage.setItem("' + key + '","' + content + '");');
    //     return this.sessionStorage.get(_key);
    // }
    public removeFromSession(key: string): any {
        // browser.executeScript('sessionStorage.removeItem("' + key + '");');
    }
}
