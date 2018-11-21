//  PROJECT:     NeoCom.Infinity(NCI.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//--- SERVICES
// import { BackendService } from '@shared';
import { AppCorePanelComponent } from './panels/app-core-panel/app-core-panel.component';
// import { BackendService } from './services/backend.service';
import { RenderComponent } from './renders/render/render.component';
import { SharedBackendService } from 'src/app/modules/shared/services/sharedbackend.service';
import { WelcomePageComponent } from 'src/app/modules/shared/pages/welcome-page/welcome-page.component';
// import { RenderComponent } from '@app';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    //--- SERVICES
    SharedBackendService,
    //--- COMPONENTS
    AppCorePanelComponent,
    RenderComponent,
    // --- PAGES
    WelcomePageComponent
  ],
  exports: [
    //--- SERVICES
    SharedBackendService,
    //--- COMPONENTS
    AppCorePanelComponent,
    RenderComponent,
    // --- PAGES
    WelcomePageComponent
  ]
})
export class NeoComSharedModule { }
