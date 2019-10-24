import { environment } from './environment';
// import { appconfiguration } from './appconfiguration';
export class IsolationService {
    // public getAppName(): string {
    //     return appconfiguration.appName;
    // }
    // public getAppVersion(): string {
    //     return appconfiguration.appVersion;
    // }
    public getCopyright(): string {
        return environment.copyright;
    }
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
}
