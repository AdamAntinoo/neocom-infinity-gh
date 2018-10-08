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
import { BackendService } from '@shared';
import { AppCorePanel } from './panels/app-core-panel/app-core-panel.component';
import { RenderComponent } from '@app';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    //--- SERVICES
    BackendService,
    //--- COMPONENTS
    AppCorePanel,
    RenderComponent
],
  exports: [
    //--- SERVICES
    BackendService,
    //--- COMPONENTS
    AppCorePanel,
    RenderComponent
  ]
})
export class NeoComSharedModule { }
