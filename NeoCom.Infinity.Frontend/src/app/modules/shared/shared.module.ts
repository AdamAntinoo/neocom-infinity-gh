// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - ROUTING
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
// - GUARDS
import { TokenAuthorizationGuard } from '@app/security/token-authorization.guard';

// - PAGES
// - PANELS
import { AppInfoPanelComponent } from './panels/app-info-panel/app-info-panel.component';
import { ServerInfoPanelComponent } from './panels/server-info-panel/server-info-panel.component';
import { CorporationPublicDataPanelComponent } from './panels/corporation-public-data-panel/corporation-public-data-panel.component';
import { PilotPublicDataPanelComponent } from './panels/pilot-public-data-panel/pilot-public-data-panel.component';

// - MODULE ROUTES
const routes: Routes = [
    // { path: '', component: DashboardHomePage, canActivate: [TokenAuthorizationGuard] },
    // { path: 'home', component: DashboardHomePage, canActivate: [TokenAuthorizationGuard] },
    // { path: 'servicios', component: AdminServiciosPageComponent, canActivate: [AuthAdminGuard] }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(routes),
    ],
    declarations: [
        // - PANELS
        AppInfoPanelComponent,
        ServerInfoPanelComponent,
        CorporationPublicDataPanelComponent,
        PilotPublicDataPanelComponent,
    ],
    exports: [
        RouterModule,
        AppInfoPanelComponent,
        ServerInfoPanelComponent,
        CorporationPublicDataPanelComponent,
        PilotPublicDataPanelComponent,
    ]
})
export class SharedModule { }
