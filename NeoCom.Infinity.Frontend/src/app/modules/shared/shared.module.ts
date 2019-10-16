// - CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
// - ROUTING
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
import { DashboardHomePage } from 'e2e/src/pages/DashboardHome.page';
// - GUARDS
import { TokenAuthorizationGuard } from '@app/security/token-authorization.guard';

// - PAGES
// - PANELS
import { AppInfoPanelComponent } from './panels/app-info-panel/app-info-panel.component';

// - MODULE ROUTES
const routes: Routes = [
    { path: '', component: DashboardHomePage, canActivate: [TokenAuthorizationGuard] },
    { path: 'home', component: DashboardHomePage, canActivate: [TokenAuthorizationGuard] },
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
    ],
    exports: [
        RouterModule
    ]
})
export class SharedModule { }
