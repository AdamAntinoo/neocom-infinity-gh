//  PROJECT:     NeoCom.Infinity(NCI.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1
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
// import { OAuthService } from 'angular-oauth2-oidc';
//--- COMPONENTS
import { AppCorePanelComponent } from 'src/app/modules/shared/panels/app-core-panel/app-core-panel.component';
import { ESIConfiguration } from 'src/app/modules/shared/models/conf/ESI.Tranquility';
import { OAuthService } from 'src/app/modules/angular-oauth2-oidc';
import { AuthConfig } from 'src/app/modules/angular-oauth2-oidc';
// import { OAuthService, AuthConfig } from 'dist/lib';

// import { AuthConfig } from 'angular-oauth2-oidc';

const authConfig: AuthConfig = {
  // responseType : 'code',
  // Url of the Identity Provider
  // issuer: 'https://steyer-identity-server.azurewebsites.net/identity',

  // // URL of the SPA to redirect the user to after login
  // redirectUri: window.location.origin + '/index.html',

  // // The SPA's id. The SPA is registerd with this id at the auth-server
  // clientId: 'spa-demo',

  // // set the scope for the permissions the client should request
  // // The first three are defined by OIDC. The 4th is a usecase-specific one
  // scope: 'openid profile email voucher',



  // Setup authorization oauth configuration properties.
  showDebugInformation: true,
  requestAccessToken: false,
  // Login-Url
  loginUrl: ESIConfiguration.AUTHORIZE_URL,
  // URL of the SPA to redirect the user to after login
  redirectUri: ESIConfiguration.CALLBACK,
  // The SPA's id. Register SPA with this id at the auth-server
  clientId: ESIConfiguration.CLIENT_ID,
  // The name of the auth-server that has to be mentioned within the token
  issuer: ESIConfiguration.AUTHORIZE_URL,
  // set the scope for the permissions the client should request
  scope: ESIConfiguration.SCOPE,
  // set to true, to receive also an id_token via OpenId Connect (OIDC) in addition to the
  // OAuth2-based access_token
  oidc: false,
  // override the default token flow for the specific code flow that is the one implemented on Eve Online
  responseType: 'code',
  // Use setStorage to use sessionStorage or another implementation of the TS-type Storage
  // instead of localStorage
  //  this.oauthService.setStorage(sessionStorage);
  // To also enable single-sign-out set the url for your auth-server's logout-endpoint here
  logoutUrl: ESIConfiguration.AUTHORIZATION_SERVER + "account/logoff"




}

/**
The Execution flow for this page is to show some Application information and then await for the user login through as ESI authorization. Because once the authorization is create some data is stored on the backend server we should create a unique client session data so nobody is able to access the authorized data.

We use RSA public key encryption to adjoin to the token so the server backend can create a unique encrypted session that is only able to be decoed by the particular client that initiated it because the key is just created on every login.
*/
@Component({
  selector: 'welcome-page',
  templateUrl: './welcome-page.component.html',
  styleUrls: ['./welcome-page.component.scss']
})
export class WelcomePageComponent extends AppCorePanelComponent implements OnInit {
  // public working: boolean = false;
  // public code: string;

  //--- C O N S T R U C T O R
  constructor(protected oauthService: OAuthService) {
    super();
  }

  //--- L I F E C Y C L E   F L O W
  /**
   * Read the authorization token from the Sore cache or from the Session storage. If not found at any of those places then we shoud show the page and allow the user to start the authorization flow.
   */
  ngOnInit() {
    this.oauthService.configure(authConfig);
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
    // override the default token flow for the specific code flow that is the one implemented on Eve Online
    // this.oauthService.responseType = 'code';
    // Use setStorage to use sessionStorage or another implementation of the TS-type Storage
    // instead of localStorage
    this.oauthService.setStorage(sessionStorage);
    // To also enable single-sign-out set the url for your auth-server's logout-endpoint here
    this.oauthService.logoutUrl = ESIConfiguration.AUTHORIZATION_SERVER + "account/logoff";

    // Check the session validity. If the session exists and it is valid then go to the dashboard page.
    // this.appStoreService.accessAuthorizationToken()
    //   .subscribe((token) => {
    //     if (null != token)
    //       this.router.navigate(['dashboard']); // The tokes exists, so we can skip the authorization flow.
    //   });
  }

  //--- P A G E   E V E N T S
  public launchLogin() {
    console.log(">> [WelcomePageComponent.launchLogin]");
    // Show the validation spinning while we get the authorization credentials.
    this.downloading = true;
    // Start the OAuth flow.
    console.log(">< [WelcomePageComponent.initImplicitFlow]");
    this.oauthService.initImplicitFlow();
    console.log("<< [WelcomePageComponent.launchLogin]");
  }
}
