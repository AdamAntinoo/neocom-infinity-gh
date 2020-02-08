// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
// - SERVICES
import { AppStoreService } from '@app/services/appstore.service';
// - DOMAIN
import { TabDefinition } from '@app/domain/TabDefinition.domain';


@Component({
    selector: 'tab-container-panel',
    templateUrl: './tab-container-panel.component.html',
    styleUrls: ['./tab-container-panel.component.scss']
})
export class TabContainerPanelComponent implements OnInit {
    @Input() tabName: string = '-NONE-'; // The name of the tab to be selected.
    public tabs: TabDefinition[];
    constructor(protected appStoreService: AppStoreService) { }

    public ngOnInit() {
        // Read the tab definitions.
        this.propertiesTabDefinitions()
            .subscribe(tabs => {
                this.tabs = tabs;
            });
    }
    private propertiesTabDefinitions(): Observable<TabDefinition[]> {
        console.log("><[TabContainerPanelComponent.propertiesTabDefinitions]");
        // Construct the request to call the backend.
        let request = 'tab-definitions';
        return this.appStoreService.accessProperties(request)
            .pipe(map(data => {
                console.log("><[TabContainerPanelComponent.propertiesTabDefinitions]> Converting data to TabDefinition's");
                let results: TabDefinition[] = [];
                if (data instanceof Array) {
                    for (let key in data) {
                        results.push(new TabDefinition(data[key]));
                    }
                } else results.push(new TabDefinition(data));
                return results;
            }));
    }
}
