//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5.2.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE MODULES
import { NgModule } from '@angular/core';
//--- BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- DRAG AND DROP
import { NgDragDropModule } from 'ng-drag-drop';
//--- OAUTH2
import { OAuthModule } from 'angular-oauth2-oidc';
//--- TOAST NOTIFICATIONS
import { ToasterModule } from 'angular5-toaster';
import { ToasterContainerComponent } from 'angular5-toaster';
import { ToasterService } from 'angular5-toaster';
//--- ADDITIONAL MODULES
//--- APPLICATION MODULES

//--- SERVICES
// import { AppModelStoreService } from './services/app-model-store.service';
//--- COMPONENTS-CORE
import { AppComponent } from './app.component';
// import { ComponentFactoryComponent } from 'app/components/component-factory/component-factory.component';
//--- DIRECTIVES
//--- PIPES
//--- PAGES
//--- COMPONENTS-CORE


@NgModule({
  imports: [
    //--- CORE MODULES
    NgModule,
    //--- BROWSER & ANIMATIONS
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    HttpModule,
    HttpClientModule,
    //--- ROUTING
    AppRoutingModule,
    //--- DRAG AND DROP
    NgDragDropModule,
    //--- OAUTH2
    OAuthModule,
    //--- TOAST NOTIFICATIONS
    ToasterModule,
    ToasterContainerComponent,
    ToasterService
  ],
  declarations: [
    //--- COMPONENTS-CORE
    AppComponent
  ],
  providers: [
    // AppModelStoreService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
