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
import { Observable } from 'rxjs';
// import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
//--- ENVIRONMENT
import { environment } from 'app/../environments/environment';
//--- HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
//--- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- ROUTER
import { Router } from '@angular/router';
// import { ActivatedRoute } from '@angular/router';
//--- WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';

// //--- NOTIFICATIONS
// // import { ToastsManager } from 'ng2-toastr/ng2-toastr';
// //--- HTTP PACKAGE
// import { Http } from '@angular/http';
// // import { HttpClient } from '@angular/common/http';
// import { Response, Headers, RequestOptions } from '@angular/http';
// import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
// //--- SERVICES
// import { ToasterService } from 'angular5-toaster';
//--- INTERFACES
// // import { IViewer } from 'app/interfaces/IViewer.interface';
import { INode } from 'app/interfaces/INode.interface';
// import { INeoComNode } from 'app/interfaces/INeoComNode.interface';
//--- MODELS
import { NeoComException } from 'app/models/ui/NeoComException.model';
// import { NeoComNode } from 'AppModelStoreService/models/NeoComNode.model';
// import { Credential } from '../models/Credential.model';
// import { Pilot } from '../models/Pilot.model';
// import { Corporation } from '../models/Corporation.model';
// import { NeoComAsset } from '../models/NeoComAsset.model';
// import { Fitting } from '../models/Fitting.model';
// import { FittingRequest } from '../models/FittingRequest.model';
// import { Action } from '../models/Action.model';
// import { Contract } from '../models/Contract.model';
// import { Job } from '../models/Job.model';
// import { MarketOrder } from 'app/models/MarketOrder.model';
// import { Property } from 'app/models/Property.model';
// import { EveItem } from 'app/models/EveItem.model';
// import { ManufactureResourcesRequestv1 } from 'app/models/industry/ManufactureResourcesRequestv1.model';
// import { RefiningProcess } from 'app/models/industry/RefiningProcess.model';
// import { FacetedResourceContainer } from 'app/models/industry/FacetedResourceContainer.model';
// import { ProcessingAction } from 'app/models/planetary/ProcessingAction.model';

export class AppModelStoreServiceDefinitions {
  //--- ~M O C K   S E C T I O N
  // Define mock data references to input data on files.
  protected responseTable = {
    '/api/v1/centros':
      '/assets/mockdata/centros.json'
  }
  public getMockStatus(): boolean {
    return environment.mockStatus;
  }

  //--- C O N S T A N T S
  protected static BACKEND_SERVER_NAME = environment.serverName;
  protected static API_VERSION = environment.apiVersion;
  protected static RESOURCE_SERVICE_URL: string = AppModelStoreServiceDefinitions.BACKEND_SERVER_NAME +
    AppModelStoreServiceDefinitions.API_VERSION;
  // - S T O R A G E   K E Y S
  public static CREDENTIAL_ISVALID_KEY: string = "-CREDENTIAL-ISVALID-KEY-";
  public static CREDENTIAL_KEY: string = "-CREDENTIAL-KEY-";
  public static TEMPLATE_KEY: string = "-TEMPLATE-KEY-";

  // protected static APPLICATION_SERVICE_PORT: number = environment.servicePort;
  // protected static RESOURCE_SERVICE_URL: string = "/api/v1";


  //--- C O N S T R U C T O R
  constructor(
    // @Inject(SESSION_STORAGE) protected storage: WebStorageService,
    protected http: HttpClient,
    protected router: Router,
    protected toasterService: NotificationsService) { }

  //--- E N V I R O N M E N T    C A L L S
  public getApplicationName(): string {
    return environment.name;
  }
  public getApplicationVersion(): string {
    return environment.version;
  }
  public inDevelopment(): boolean {
    return environment.development;
  }

  //--- G L O B A L   F U C T I O N A L I T I E S
  public isNonEmptyString(str: string): boolean {
    return str && str.length > 0; // Or any other logic, removing whitespace, etc.
  }
  public isNonEmptyArray(data: any[]): boolean {
    if (null == data) return false;
    if (data.length < 1) return false;
    return true;
  }

  //--- B A C K E N D   U T I L I T I E S
  protected wrapHttpGETCall(request: string): Observable<any> {
    // Generate the new headers to limit data access tampering. Time validity timer set to 15 minutes.
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    console.log("><[AppModelStoreServiceDefinitions.wrapHttpGETCall]> request: " + request);
    return this.http.get(request, { headers: headers });
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
      // if (jclass == "Exception") {
      //   let convertedException = new NeoComException(entrydata);
      //   console.log("--[AppModelStoreService.transformRequestOutput]> Exception node: " + convertedException.code);
      //   // Report the exception to the Notification
      //   this.toasterService.pop('error', convertedException.code, convertedException.message);
      //   return [];
      // } else {
      // // Return a single item converted.
      // return this.convertNode(entrydata as INeoComNode);
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
      // case "Cita":
      //   let convertedCita = new Cita(node);
      //   console.log("--[AppModelStoreService.convertNode]> Cita node: " + convertedCita.huecoId);
      //   return convertedCita;
      // case "Proveedor":
      //   let convertedProveedor = new Proveedor(node);
      //   console.log("--[AppModelStoreService.convertNode]> Proveedor node: " + convertedProveedor.getId());
      //   return convertedProveedor;
      default:
        return new NeoComException().setMessage(JSON.stringify(node));
      //   switch (node.jsonClass) {
      //     case "Credential":
      //       let convertedCredential = new Credential(node);
      //       console.log("--[AppModelStoreService.convertNode]> Credential node: " + convertedCredential.getAccountId());
      //       return convertedCredential;
      //     case "Fitting":
      //       let convertedFitting = new Fitting(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Fitting node: " + convertedFitting.getShipName());
      //       return convertedFitting;
      //     case "Action":
      //       let convertedAction = new Action(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Action node: " + convertedAction.getResourceName());
      //       return convertedAction;
      //     case "NeoComAsset":
      //       let convertedAsset = new NeoComAsset(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified NeoComAsset node: " + convertedAsset.getName());
      //       return convertedAsset;
      //     case "Contract":
      //       let convertedContract = new Contract(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Contract node: " + convertedContract.getContractId());
      //       return convertedContract;
      //     case "Job":
      //       let convertedJob = new Job(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Contract node: " + convertedJob.getJobId());
      //       return convertedJob;
      //     case "FittingRequest":
      //       let convertedFittingRequest = new FittingRequest(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified FittingRequest node: " + convertedFittingRequest.getRequestId());
      //       return convertedFittingRequest;
      //     case "Corporation":
      //       let convertedCorporation = new Corporation(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Corporation node: " + convertedCorporation.getId());
      //       return convertedCorporation;
      //     case "Pilot":
      //       let convertedPilot = new Pilot(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Pilot node: " + convertedPilot.getId());
      //       return convertedPilot;
      //     case "MarketOrder":
      //       let convertedOrder = new MarketOrder(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified MarketOrder node: " + convertedOrder.getTypeId());
      //       return convertedOrder;
      //     case "Property":
      //       let convertedProperty = new Property(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified Property node: " + convertedProperty.getPropertyType());
      //       return convertedProperty;
      //     case "ManufactureResourcesRequest":
      //       let convertedRequest = new ManufactureResourcesRequestv1(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified ManufactureResourcesRequest node: " + convertedRequest.getTargetName());
      //       return convertedRequest;
      //     case "EveItem":
      //       let convertedItem = new EveItem(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified EveItem node: " + convertedItem.getName());
      //       return convertedItem;
      //     case "RefiningProcess":
      //       let convertedRefProcess = new RefiningProcess(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified RefiningProcess node: " + convertedRefProcess.getName());
      //       return convertedRefProcess;
      //     case "FacetedResourceContainer":
      //       let convertedFacetedResource = new FacetedResourceContainer(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified FacetedResourceContainer node: " + convertedFacetedResource.getFacetName());
      //       return convertedFacetedResource;
      //     case "ProcessingAction":
      //       let convertedProcessingAction = new ProcessingAction(node);
      //       console.log("--[AppModelStoreService.transformRequestOutput]> Identified ProcessingAction node: " + convertedProcessingAction.getTypeId());
      //       return convertedProcessingAction;
      //     default:
      //       return new NeoComException().setMessage(JSON.stringify(node));
      //   }
      // }
    }
  }
}
