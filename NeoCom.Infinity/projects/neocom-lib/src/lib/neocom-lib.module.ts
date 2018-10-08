//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE MODULES
import { NgModule } from '@angular/core';
//--- BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
// import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
import { HttpClientModule } from '@angular/common/http';
//--- TOAST NOTIFICATIONS
import { SimpleNotificationsModule } from 'angular2-notifications';
import { NotificationsService } from 'angular2-notifications';
//--- WEBSTORAGE
import { StorageServiceModule } from 'angular-webstorage-service';
//--- SERVICES
import { AppStoreService } from './services/appstore.service';
//--- COMPONENTS
// import { CitamedLibComponent } from './citamed-lib.component';
// import { BasePageComponent } from './basepage.component';
// import { NodeComponent } from './components/node.component';
// import { ExpandableNodeComponent } from './components/expandablenode.component';
// import { CentroComponent } from './components/centro/centro.component';
// import { EspecialidadComponent } from './components/especialidad/especialidad.component';
// import { MedicoComponent } from './components/medico/medico.component';
// import { CitaComponent } from './components/cita/cita.component';


@NgModule({
  imports: [
    //--- BROWSER & ANIMATIONS
    FormsModule,
    // ReactiveFormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    HttpClientModule,
    //--- TOAST NOTIFICATIONS
    SimpleNotificationsModule.forRoot(),
    //--- WEBSTORAGE
    StorageServiceModule
  ],
  declarations: [
    //--- SERVICES
    // AppStoreService,
    //--- COMPONENTS
    // BasePageComponent
  ],
  exports: [
    //--- SERVICES
    // AppStoreService,
    //--- COMPONENTS
    // BasePsageComponent
  ],
  providers: [
    AppStoreService
  ]
})
export class NeocomLibModule { }
