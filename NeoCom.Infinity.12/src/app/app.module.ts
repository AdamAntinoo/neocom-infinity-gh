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
//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
import { HttpClientModule } from '@angular/common/http';
//--- TOAST NOTIFICATIONS
import { SimpleNotificationsModule } from 'angular2-notifications';
import { NotificationsService } from 'angular2-notifications';
//--- OAUTH2
import { OAuthModule } from 'angular-oauth2-oidc';
//--- WEBSTORAGE
import { StorageServiceModule } from 'angular-webstorage-service';
//--- DRAG AND DROP
// import { NgDragDropModule } from 'ng-drag-drop';
//--- ADDITIONAL MODULES

//--- APPLICATION MODULES
import { UIModule } from './modules/ui/ui.module';
import { AuthenticationModule } from './modules/authentication/authentication.module';
// import { NeoComModelsModule } from './modules/neocom-models/neocom-models.module';
// import { FittingModule } from './modules/fitting/fitting.module';
// import { AssetsModule } from './modules/assets/assets.module';
// import { MenuBarModule } from './modules/menubar/menubar.module';
// import { IndustryModule } from './modules/industry/industry.module';
// // import { IncubationModule } from './modules/incubation/incubation.module';
// import { PlanetaryModule } from './modules/planetary/planetary.module';

// import { IncubationModule } from './modules/incubation/incubation.module';
//--- SERVICES
import { AppModelStoreService } from './services/app-model-store.service';
import { AppStoreService } from './services/appstore.service';
//--- COMPONENTS-CORE
import { AppComponent } from './app.component';

@NgModule({
  imports: [
    //--- ROUTING
    AppRoutingModule,
    //--- BROWSER & ANIMATIONS
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    HttpClientModule,
    //--- TOAST NOTIFICATIONS
    SimpleNotificationsModule.forRoot(),
    //--- OAUTH2
    OAuthModule.forRoot(),
    //--- WEBSTORAGE
    StorageServiceModule,
    //--- APPLICATION MODULES
    UIModule,
    AuthenticationModule
  ],
  declarations: [
    AppComponent,
    // BasePageComponent
  ],
  providers: [
    AppModelStoreService,
    NotificationsService,
    AppStoreService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
