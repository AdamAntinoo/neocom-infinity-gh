//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component, OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
//--- HTTP PACKAGE
import { Http } from '@angular/http';
import { Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- INTERFACES
import { PageComponent } from 'src/app/classes/PageComponent';
//--- MODELS
import { Credential } from 'src/app/models/Credential.model';
import { NeoComNode } from 'src/app/models/NeoComNode.model';
import { NeoComCharacter } from 'src/app/models/NeoComCharacter.model';
import { Pilot } from 'src/app/models/Pilot.model';
import { Fitting } from 'src/app/models/Fitting.model';

import { Corporation } from 'src/app/models/Corporation.model';
import { Manager } from 'src/app/models/Manager.model';
import { AssetsManager } from 'src/app/models/AssetsManager.model';
import { PlanetaryManager } from 'src/app/models/PlanetaryManager.model';
import { ProcessingAction } from 'src/app/models/ProcessingAction.model';
import { Separator } from 'src/app/models/Separator.model';
import { Login } from 'src/app/models/Login.model';

/**
This service will store persistent application data and has the knowledge to get to the backend to retrieve any data it is requested to render on the view.
*/
@Injectable()
export class AppModelStoreMockService {
  // static APPLICATION_NAME: string = "NeoCom-MS";
  // static APPLICATION_VERSION: string = "v 0.11.0"
  // static APPLICATION_SERVICE_PORT = "9000";
  // // static RESOURCE_SERVICE_URL: string = "http://localhost:" + AppModelStoreService.APPLICATION_SERVICE_PORT + "/api/v1";
  //
  // private _credentialList: Credential[] = null; // List of Credential data. It also includes the Pilotv1 information.
  // private _currentCharacter: Pilot = null; // The current active character
  //
  // private _loginList: Login[] = null; // List of Login structures to be used to aggregate Keys
  // private _currentLogin: Login = null; // The current Login active.
  // private _lastViewer: PageComponent = null;

  // constructor(private http: Http, private router: Router) { }

  //--- C O M M O N    C A L L S
  // public getApplicationName(): string {
  //   return AppModelStoreService.APPLICATION_NAME;
  // }
  // public getApplicationVersion(): string {
  //   return AppModelStoreService.APPLICATION_VERSION;
  // }

  //--- S T O R E   F I E L D S    A C C E S S O R S
  //--- C R E D E N T I A L    S E C T I O N

  // --- P I L O T   S E C T I O N

  //--- B A C K E N D    C A L L S

  //--- P I L O T   S E C T I O N

  //--- C A L L B A C K   S E C T I O N
}
