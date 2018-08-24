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
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
//--- HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
//--- ENVIRONMENT
import { environment } from 'src/environments/environment';
//--- SERVICES
import { AppModelStoreServiceStorage } from 'app/services/app-model-store.storage.service';
// import { ENVIRONMENT_CONFIG } from '../environment.config';
// import { IEnvironmentService } from '../environment.config';
//--- WEBSTORAGE
// import { LOCAL_STORAGE } from 'angular-webstorage-service';
// import { WebStorageService } from 'angular-webstorage-service';
// //--- ROUTER
// import { Router } from '@angular/router';
// import { ActivatedRoute } from '@angular/router';
// //--- NOTIFICATIONS
// import { NotificationsService } from 'angular2-notifications';
//--- INTERFACES
import { INode } from 'app/interfaces/INode.interface';
// //--- SERVICES
// import { AppModelStoreServiceBackend } from 'app/services/app-model-store.backend.service';
//--- MODELS
import { LabelNode } from 'app/models/core/LabelNode.model';
import { Pilot } from 'app/models/Pilot.model';
// import { Medico } from '../models/Medico.model';
// import { Cita } from '../models/Cita.model';

//--- C O N S T A N T S
const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

/**
This service will store persistent application data and has the knowledge to get to the backend to retrieve any data it is requested to render on the view.
*/
@Injectable()
export class AppStoreService extends AppModelStoreServiceStorage {
  // //--- MOCK SECTION
  // // Define mock data references to input data on files.
  // protected responseTable = {
  //   // '/api/v1/centros':
  //   //   '/assets/mockdata/centros.json'
  // }
  // public getMockStatus(): boolean {
  //   return this.environment.mockStatus;
  // }

  //--- C O N S T A N T S
  // protected static BACKEND_SERVER_NAME: string = "-EMPTY-";
  // protected static RESOURCE_SERVICE_URL: string = "-EMPTY-";

  //--- C O N S T R U C T O R
  // constructor(
  //   @Inject(LOCAL_STORAGE) protected storage: WebStorageService,
  //   protected http: HttpClient,
  //   protected router: Router,
  //   protected toasterService: NotificationsService,
  //   @Inject(ENVIRONMENT_CONFIG) protected environment: IEnvironmentService) {
  //   AppStoreService.BACKEND_SERVER_NAME = this.environment.serverName;
  //   AppStoreService.RESOURCE_SERVICE_URL = AppStoreService.BACKEND_SERVER_NAME + "/api/v1";
  // }

  //--- P I L O T   S E C T I O N
  /**
  Get access to the PilotPublicInformation data block. The function will perform the session validity check before passing back the call to the backend interface but only if the data is not already present.
  The standard response should be to get the already available block from the session cache and return it. But if the cache has been invalidated (because some other UI action) or if we are in development and we reload the application we can face the situation where we have a valid session but not data block in the cache. Is such cases we should be able to retriebe again the public information.
  */
  public accessPilotPublicData4Id(/*id: number*/): Observable<Pilot> {
    console.log("><[AppModelStoreServicePilot.accessPilotPublicData4Id]");
    // Check if we already have the data loaded at the Session/Credential.
    if (null != this._session) {
      let cred = this._session.getCredential();
      if (null != cred) {
        let pilot = cred.getPilot();
        if (null != pilot) {
          return new Observable(observer => {
            console.log("><[AppModelStoreServicePilot.accessPilotPublicData4Id]> Pilot found on cache data.");
            observer.next(pilot);
          });
        } else {
          // Post a request to the backend to get the public data.
          return this.getBackendPilotPublicData(cred.getAccountId());
        }
      } else {
        // The credential is null so there is not a completed login. Go back to the initial page.
        throw new Error('Credential is null. Required data not found.');
      }
    } else {
      // The session is null so there is not a completed login. Go back to the initial page.
      throw new Error('Session is null. Required data not found.');
    }
    // This should be returned while the navigation is executed.
    // return this.getBackendPilotPublicData(id);
  }
  //--- P I L O T    S E C T I O N
  /**
  Get from the backend the Pilot public data and all dependant pulic information like the Corporation, Alliance and other fields. This just retrieves the core data and not all other related data blocks like Fittings or Assets or any other additional information.
  */
  public getBackendPilotPublicData(pilotid: number): Observable<Pilot> {
    console.log("><[AppModelStoreServicePilot.getBackendPilotPublicData]> Pilotid = " + pilotid);
    // Construct the request to call the backend.
    let request = AppStoreService.RESOURCE_SERVICE_URL + "/pilot/" + pilotid + "/publicdata";
    // Call the HTTP wrapper to construct the request and fetch the data.
    return this.wrapHttpGETCall(request)
      .pipe(map(data => {
        // Transform received data and return the node list to the caller for aggregation.
        let pilot = this.transformRequestOutput(data)[0] as Pilot;
        // Store a copy on the session.
        this._session.storePilot(pilot);
        return pilot;
      }));
  }


  //--- P R I V A T E    S E C T I O N
  protected wrapHttpGETCall(request: string): Observable<any> {
    // Check if we should use mock data.
    if (this.getMockStatus()) {
      // Search for the request at the mock map.
      let hit = this.responseTable[request];
      if (null != hit) request = hit;
    }
    // Generate the new headers to limit data access tampering. Time validity timer set to 15 minutes.
    console.log("><[AppStoreService.wrapHttpGETCall]> request: " + request);
    let requestHeaders = headers;
    requestHeaders.append("xNeocom-Session-Locator", this._session.getSessionLocator())
    return this.http.get(request, { headers: requestHeaders });
  }
  public transformRequestOutput(entrydata: any): INode[] {
    let results: INode[] = [];
    // Check if the entry data is a single object. If so process it because can be an exception.
    if (entrydata instanceof Array) {
      for (let key in entrydata) {
        // Access the object into the spot.
        let node = entrydata[key] as INode;
        // Convert and add the node.
        results.push(this.convertNode(node));
      }
    } else {
      // Process a single element.
      let jclass = entrydata["jsonClass"];
      if (null == jclass) return [];
      // Process the entry data as an array.
      let list = [];
      list.push(entrydata);
      return this.transformRequestOutput(list);
      // }
    }
    return results;
  }
  protected convertNode(node): INode {
    switch (node.jsonClass) {
      // case "Centro":
      //   let convertedCentro = new Centro(node);
      //   console.log("--[AppModelStoreService.convertNode]> Centro node: " + convertedCentro.getId());
      //   return convertedCentro;
      // case "Medico":
      //   let convertedMedico = new Medico(node);
      //   console.log("--[AppModelStoreService.convertNode]> Medico node: " + convertedMedico.getId());
      //   return convertedMedico;
      // case "Cita":
      //   let convertedCita = new Cita(node);
      //   console.log("--[AppModelStoreService.convertNode]> Cita node: " + convertedCita.huecoId);
      //   return convertedCita;
      default:
        return new LabelNode({ title: "Conversion class " + node.jsonClass + " not found." });
    }
  }
}
