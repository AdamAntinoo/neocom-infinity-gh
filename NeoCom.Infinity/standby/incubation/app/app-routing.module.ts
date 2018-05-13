//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Routes } from '@angular/router';
//--- PAGES
import { WelcomePageComponent } from './pages/welcome-page/welcome-page.component';
// import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
// import { EsiAuthorizationPageComponent } from './pages/esi-authorization-page/esi-authorization-page.component';
// import { ProcessingRenderPageComponent } from './pages/processing-render-page/processing-render-page.component';
// import { AuthorizationProgressPageComponent } from './pages/authorization-progress-page/authorization-progress-page.component';
import { ValidateAuthorizationPageComponent } from './pages/validate-authorization-page//validate-authorization-page.component';
import { PilotDashboardPageComponent } from './pages/pilot-dashboard-page/pilot-dashboard-page.component';
// import { DragDropPageComponent } from './pages/drag-drop-page/drag-drop-page.component';
// import { ActionrenderingPageComponent } from './pages/actionrendering-page/actionrendering-page.component';
// import { ActionsbyClassPageComponent } from './pages/actionsby-class-page/actionsby-class-page.component';
// import { FittingProcessingPageComponent } from './pages/fitting-processing-page/fitting-processing-page.component';
// import { FittingManagerPageComponent } from './pages/fitting-manager-page/fitting-manager-page.component';
// import { CorporationCardPageComponent } from './pages/corporation-card-page/corporation-card-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/welcome',
    pathMatch: 'full'
  },
  { path: 'welcome', component: WelcomePageComponent },
  // { path: 'esiauthorization', component: EsiAuthorizationPageComponent },
  // { path: 'testprocessing/:id', component: ProcessingRenderPageComponent },
  // { path: 'authorizationprogress', component: AuthorizationProgressPageComponent },
  { path: 'validateauthorization', component: ValidateAuthorizationPageComponent },
  { path: 'dashboard', component: PilotDashboardPageComponent },
  // { path: 'dragdrop', component: DragDropPageComponent },
  // { path: 'actionsbyclass', component: ActionsbyClassPageComponent },
  // { path: 'fittingprocessing/:fittingid', component: FittingProcessingPageComponent },
  //
  // { path: 'pilot/:id/fittingmanager', component: FittingManagerPageComponent },
  // // CORPORATIONS TABS
  // { path: 'corporation/:corpid', component: CorporationCardPageComponent },
  // { path: 'corporation/:corpid/members', component: CorporationCardPageComponent },
  //
  // // Paths defined on the application not present on the POC. Redirection to dashboard.
  // { path: 'credentials', component: DashboardPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
