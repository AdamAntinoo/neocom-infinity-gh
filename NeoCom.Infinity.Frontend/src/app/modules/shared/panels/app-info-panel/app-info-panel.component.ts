// - CORE
import { Component } from '@angular/core';
import { environment } from '@env/environment';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';

@Component({
    selector: 'app-info-panel',
    templateUrl: './app-info-panel.component.html',
    styleUrls: ['./app-info-panel.component.scss']
})
export class AppInfoPanelComponent {
    constructor(protected isolationService: IsolationService) { }

    public getName(): string {
        return this.isolationService.getAppName().toUpperCase();
    }
    public getVersion(): string {
        return this.isolationService.getAppVersion();
    }
    public getCopyright(): string {
        return environment.copyright;
    }
}
