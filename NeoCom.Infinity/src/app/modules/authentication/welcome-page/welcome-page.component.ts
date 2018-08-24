//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
//--- HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
//--- ROUTER
import { Router } from '@angular/router';
//--- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- SERVICES
import { OAuthService } from 'angular-oauth2-oidc';
import { AppStoreService } from 'app/services/appstore.service';

// import { OAuthService } from 'angular2-oauth2/oauth-service';
// import { AuthConfig } from 'angular-oauth2-oidc';
// import { JwksValidationHandler } from 'angular-oauth2-oidc';
// import { OAuthService, AuthConfig } from 'angular-oauth2-oidc';
// import { authConfig } from './authconfig.const';
//--- INTERFACES
// import { EVariant } from '../../classes/EVariant.enumerated';
// import { IDetailedEnabledPage } from '../../classes/IDetailedEnabledPage.interface';
// import { INeoComNode } from '../../classes/INeoComNode.interface';
//--- COMPONENTS
// import { BasePageComponent } from '../../components/core/base-page/base-page.component';
//--- MODELS
import { ESIConfiguration } from 'app/models/conf/ESI.Tranquility';
// import { Credential } from '../../models/Credential.model';
// import { authConfig } from './auth.config';

// import cryptico from 'node_modules/cryptico/lib/cryptico.js'
// import cryptico from 'cryptico';
// import * as cryptico from "cryptico";
// import { crypto } from 'crypto';

/**
The Execution flow for this page is to show some Application information and then await for the user login through as ESI authorization. Because once the authorization is create some data is stored on the backend server we should create a unique client session data so nobody is able to access the authorized data.

We use RSA public key encryption to adjoin to the token so the server backend can create a unique encrypted session that is only able to be decoed by the particular client that initiated it because the key is just created on every login.
*/
@Component({
  selector: 'welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss']
})
export class WelcomePageComponent implements OnInit {
  public working: boolean = false;
  public code: string;

  //--- C O N S T R U C T O R
  constructor(protected appStoreService: AppStoreService
    , protected http: HttpClient
    , protected oauthService: OAuthService
    , protected router: Router
    , protected toaster: NotificationsService) {
    // Setup authorization oauth configuration properties.
    this.oauthService.showDebugInformation = true;
    this.oauthService.requestAccessToken = true;
    // Login-Url
    this.oauthService.loginUrl = ESIConfiguration.AUTHORIZE_URL;
    // URL of the SPA to redirect the user to after login
    this.oauthService.redirectUri = ESIConfiguration.CALLBACK;
    // The SPA's id. Register SPA with this id at the auth-server
    this.oauthService.clientId = ESIConfiguration.CLIENT_ID;
    // The name of the auth-server that has to be mentioned within the token
    this.oauthService.issuer = ESIConfiguration.AUTHORIZE_URL;
    // set the scope for the permissions the client should request
    this.oauthService.scope = ESIConfiguration.SCOPE;
    // set to true, to receive also an id_token via OpenId Connect (OIDC) in addition to the
    // OAuth2-based access_token
    this.oauthService.oidc = false;
    // Use setStorage to use sessionStorage or another implementation of the TS-type Storage
    // instead of localStorage
    this.oauthService.setStorage(sessionStorage);
    // To also enable single-sign-out set the url for your auth-server's logout-endpoint here
    this.oauthService.logoutUrl = ESIConfiguration.AUTHORIZATION_SERVER + "account/logoff";
  }

  //--- L I F E C Y C L E   F L O W
  ngOnInit() {
    // Check the session validity. If the session exists and it is valid then go to the dashboard page.
    this.appStoreService.checkSessionActive()
      .subscribe((session) => {
        if (null != session)
          this.router.navigate(['dashboard']);
      });
  }

  //--- P A G E   E V E N T S
  public launchLogin() {
    console.log(">> [WelcomePageComponent.launchLogin]");
    // Show the validation spinning while we get the authorization credentials.
    this.working = true;
    // Start the OAuth flow.
    console.log(">< [WelcomePageComponent.initImplicitFlow]");
    this.oauthService.initImplicitFlow();
    console.log("<< [WelcomePageComponent.launchLogin]");
  }
}
