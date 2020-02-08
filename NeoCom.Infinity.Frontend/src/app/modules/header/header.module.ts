// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - ROUTING
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
// - GUARDS
import { TokenAuthorizationGuard } from '@app/security/token-authorization.guard';

import { SharedModule } from '../shared/shared.module';
// - PAGES
// - PANELS
import { CorporationPublicDataPanelComponent } from './corporation-public-data-panel/corporation-public-data-panel.component';
import { PilotPublicDataPanelComponent } from './pilot-public-data-panel/pilot-public-data-panel.component';
import { ServerInfoPanelComponent } from './server-info-panel/server-info-panel.component';
import { TabContainerPanelComponent } from './tab-container-panel/tab-container-panel.component';

// - MODULE ROUTES
const routes: Routes = [
];

@NgModule({
    imports: [
        CommonModule,
        SharedModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        // - PANELS
        CorporationPublicDataPanelComponent,
        PilotPublicDataPanelComponent,
        ServerInfoPanelComponent,
        TabContainerPanelComponent,
    ],
    exports: [
        RouterModule,
        CorporationPublicDataPanelComponent,
        PilotPublicDataPanelComponent,
        ServerInfoPanelComponent,
        TabContainerPanelComponent,
    ]
})
export class HeaderModule { }
