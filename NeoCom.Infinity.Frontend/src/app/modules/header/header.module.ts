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
import { CorporationPublicDataPanelComponent } from '../header/corporation-public-data-panel/corporation-public-data-panel.component';

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
    ],
    exports: [
        RouterModule,
        CorporationPublicDataPanelComponent,
    ]
})
export class HeaderModule { }
