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
//--- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- SERVICES
import { OAuthService } from 'angular-oauth2-oidc';
import { AppModelStoreService } from 'app/services/app-model-store.service';

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
import { ESIConfiguration } from '../../../models/conf/ESI.Tranquility';
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
export class WelcomePageComponent {
  public working: boolean = false;
  public code: string;

  constructor(protected appModelStore: AppModelStoreService
    , protected http: HttpClient
    , protected oauthService: OAuthService
    , protected toaster: NotificationsService) {

    this.oauthService.showDebugInformation = true;
    // this.oauthService.userResponseType = 'code';
    this.oauthService.requestAccessToken = true;
    // Login-Url
    this.oauthService.loginUrl = ESIConfiguration.AUTHORIZE_URL; //Id-Provider?
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
    // this.oauthService.requestAccessToken = false;
    // Use setStorage to use sessionStorage or another implementation of the TS-type Storage
    // instead of localStorage
    this.oauthService.setStorage(sessionStorage);
    // To also enable single-sign-out set the url for your auth-server's logout-endpoint here
    this.oauthService.logoutUrl = ESIConfiguration.AUTHORIZATION_SERVER + "account/logoff";
  }

  // public getDescription(): string {
  //   return "Welcome to the Infinity helper. Log in with your Eve Online credentials on the ESI login portal to get access to a capsuleer dedicated Industrialist Management System. Services provided are: Asset List - Mining Legder and Skills List."
  // }
  public launchLogin() {
    console.log(">> [WelcomePageComponent.launchLogin]");
    // Show the validation spinning while we get the authorization credentials.
    this.working = true;
    // Start the OAuth flow.
    console.log(">< [WelcomePageComponent.initImplicitFlow]");
    this.oauthService.initImplicitFlow();
    console.log("<< [WelcomePageComponent.launchLogin]");
  }
  // private generateRSAKey() {
  //   let generateKeyPromise = window.crypto.subtle.generateKey(
  //     {
  //       name: "RSA-PSS",
  //       modulusLength: 2048, //can be 1024, 2048, or 4096
  //       publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
  //       hash: { name: "SHA-256" }, //can be "SHA-1", "SHA-256", "SHA-384", or "SHA-512"
  //     },
  //     false, //whether the key is extractable (i.e. can be used in exportKey)
  //     ["sign", "verify"] //can be any combination of "sign" and "verify"
  //   );
  //   generateKeyPromise.then((key) => {
  //     this.appModelStore.setRSAKey(key);
  //     this.appModelStore.setPublicKey(key.publicKey);
  //     //returns a keypair object
  //     console.log(key);
  //     console.log(key.publicKey);
  //     console.log(key.privateKey);
  //   });
  //
  //   window.crypto.subtle.generateKey(
  //     {
  //       name: "RSA-PSS",
  //       modulusLength: 2048, //can be 1024, 2048, or 4096
  //       publicExponent: new Uint8Array([0x01, 0x00, 0x01]),
  //       hash: { name: "SHA-256" }, //can be "SHA-1", "SHA-256", "SHA-384", or "SHA-512"
  //     },
  //     false, //whether the key is extractable (i.e. can be used in exportKey)
  //     ["sign", "verify"] //can be any combination of "sign" and "verify"
  //   )
  //     .then((key) => {
  //       this.appModelStore.setRSAKey(key);
  //       this.appModelStore.setPublicKey(key.publicKey);
  //       //returns a keypair object
  //       console.log(key);
  //       console.log(key.publicKey);
  //       console.log(key.privateKey);
  //     });
  //   // .catch(function(err) {
  //   //   console.error(err);
  //   // });
  // }
}
