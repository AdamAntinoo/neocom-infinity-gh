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
import { CommonModule } from '@angular/common';
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
// import { OAuthModule } from 'angular-oauth2-oidc';
//--- WEBSTORAGE
import { StorageServiceModule } from 'angular-webstorage-service';
//--- APPLICATION COMPONENTS
import { UIModule } from '../../modules/ui/ui.module';

//--- PAGES
import { WelcomePageComponent } from './welcome-page/welcome-page.component';
import { ValidateAuthorizationPageComponent } from './validate-authorization-page/validate-authorization-page.component';

@NgModule({
  imports: [
    //--- CORE MODULES
    CommonModule,
    //--- BROWSER & ANIMATIONS
    FormsModule,
    BrowserModule,
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    HttpClientModule,
    //--- TOAST NOTIFICATIONS
    SimpleNotificationsModule.forRoot(),
    //--- OAUTH2
    // OAuthModule,
    //--- WEBSTORAGE
    StorageServiceModule,
    //--- APPLICATION MODULES
    UIModule
  ],
  declarations: [
    //--- PAGES
    WelcomePageComponent,
    ValidateAuthorizationPageComponent
  ],
  exports: [
    //--- PAGES
    WelcomePageComponent,
    ValidateAuthorizationPageComponent
  ]
})
export class AuthenticationModule { }
