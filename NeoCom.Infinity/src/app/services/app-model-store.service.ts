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
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
//--- ENVIRONMENT
import { environment } from '../../environments/environment';
//--- NOTIFICATIONS
// import { ToastsManager } from 'ng2-toastr/ng2-toastr';
//--- HTTP PACKAGE
import { Http } from '@angular/http';
// import { HttpClient } from '@angular/common/http';
import { Response, Headers, RequestOptions } from '@angular/http';
// import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
// //--- SERVICES
// import { ToasterService } from 'angular5-toaster';
// import { AppModelStoreServiceSDE } from 'app/services/app-model-store.sde.service';
// //--- INTERFACES
// import { IViewer } from 'app/interfaces/IViewer.interface';
// import { INeoComNode } from '../interfaces/INeoComNode.interface';
// //--- MODELS
// import { Credential } from '../models/Credential.model';
// import { NeoComNode } from '../models/NeoComNode.model';
// import { NeoComCharacter } from '../models/NeoComCharacter.model';
// import { Pilot } from '../models/Pilot.model';
// import { Fitting } from '../models/Fitting.model';
// import { NeoComAsset } from '../models/NeoComAsset.model';
//
// import { Corporation } from '../models/Corporation.model';
// import { Manager } from '../models/Manager.model';
// import { AssetsManager } from '../models/AssetsManager.model';
// import { PlanetaryManager } from '../models/PlanetaryManager.model';
// // import { ProcessingAction } from '../models/ProcessingAction.model';
// import { Separator } from '../models/Separator.model';
// import { Login } from '../models/Login.model';
//
// import { Action } from '../models/Action.model';
// import { Contract } from '../models/Contract.model';
// import { Job } from '../models/Job.model';
// import { FittingRequest } from '../models/FittingRequest.model';

/**
This service will store persistent application data and has the knowledge to get to the backend to retrieve any data it is requested to render on the view.
*/
@Injectable()
export class AppModelStoreService /*extends AppModelStoreServiceSDE*/ {
}
