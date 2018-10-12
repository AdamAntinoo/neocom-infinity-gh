//  PROJECT:     NeoCom.Infinity(NCI.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
// --- CORE
import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
// --- IONIC
import { CacheService } from 'ionic-cache';
// --- ENVIRONMENT
import { environment } from 'src/environments/environment';
// --- WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
// --- HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// --- ROUTER
import { Router } from '@angular/router';
// --- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- DATE FUNCTIONS
import { differenceInMilliseconds } from 'date-fns';
// --- SERVICES
import { MockUpBackendService } from './mockup-backend.service';
// --- INTERFACES
// import { INode } from 'citamed-lib';
import { INeoComNode } from '../interfaces/INeoComNode.interface';
// --- MODELS
import { Pilot } from '../models/Pilot.model';

@Injectable({
  providedIn: 'root'
})
export class SharedBackendService extends MockUpBackendService {
  // --- C O N S T R U C T O R
  constructor(
    // @Inject(LOCAL_STORAGE) protected storage: WebStorageService,
    // @Inject(SESSION_STORAGE) protected sessionStorage: WebStorageService,
    protected http: HttpClient,
    protected router: Router,
    protected toasterService: NotificationsService,
    protected cache: CacheService) {
    super();
    cache.setDefaultTTL(60 * 2); // set to 2 minutes.
  }

  //--- G L O B A L   F U C T I O N A L I T I E S
  public isNonEmptyString(str: string): boolean {
    return str && str.length > 0; // Or any other logic, removing whitespace, etc.
  }


  //--- STORE FIELDS
  // protected _rsaKey = null; // RSA session key generated on the login process.
  // protected _publicKey = null; // Local public key from the RSA key to be used on message decryption by backend.
  // protected _sessionIdentifier: string = "-MOCK-SESSION-ID-"; // Unique session identifier to be interchanged with backend.
  // protected _session: NeoComSession = null;
  // protected _credentialList: Credential[] = null; // List of Credential data. It also includes the Pilotv2 information.
  // protected _pilotIdentifier: number = 92002067; // Pilot identifier number for this session.

  //--- C O M M O N    C A L L S
  // public myUniqueID(): string {
  //   return Math.random().toString(36).slice(2);
  // }

  //--- S T O R E   F I E L D S    A C C E S S O R S
  //--- S E S S I O N   A C C E S S O R S
  // public checkSessionActive(): Observable<NeoComSession> {
  //   if (null == this._session) {
  //     this.toasterService.pop('warning', 'SESSION NOT LOADED', 'There is not valid session. Going back to initial page!.');
  //     this.router.navigate(['dashboard']);
  //   }
  //   return new Observable(observer => {
  //     setTimeout(() => {
  //       observer.next(this._session);
  //     }, 500);
  //     setTimeout(() => {
  //       observer.complete();
  //     }, 500);
  //   });
  // }
  // public getPublicKey(): CryptoKey {
  //   return this._publicKey;
  // }
  // // @deprecated()
  // public getActivePilot(): number {
  //   return this._pilotIdentifier;
  // }
  // public getActivePilotIdentifier(): number {
  //   return this._pilotIdentifier;
  // }
  // public setRSAKey(key) {
  //   this._rsaKey = key;
  // }
  // public setPublicKey(key: CryptoKey) {
  //   this._publicKey = key;
  // }
  // public setSession(newsession: NeoComSession): void {
  //   this._session = newsession;
  //   this.toasterService.pop('success', 'SESSION OK', 'Session properly loaded.');
  // }
  // public setSessionIdentifier(sessionId: string): void {
  //   this._sessionIdentifier = sessionId;
  // }
  // public setPilotIdentifier(pilotid: number): void {
  //   this._pilotIdentifier = pilotid;
  // }

  // --- B A C K E N D   C A L L S   A C C E S S O R S
  // - E S I - A U T H O R I Z A T I O N   F L O W
  /**
*  Go to the backend and use the ESI api to exchange the authorization code by the refresh token. Then also store the new credential on the database and clear the list of credentials to force a new reload the next time we need them.
 * This call is not mocked up so requires the backend server to be up.
*
 * Add to the information to sent to the backend the PublicKey to be used on this session encryption.
  */
  public backendExchangeAuthorization(code: string): void {
    console.log("><[AppModelStoreService.backendExchangeAuthorization]");
    let request = environment.serverName + environment.apiVersion1 + "/exchangeauthorization/" + code;
    console.log("--[BackendService.backendCitaCreationProcess]> request = " + request);
    // The code is already encripted and single use. No need to add more encryption.
    // Call the HTTP wrapper to construct the request and fetch the data.
    this.wrapHttpGETCall(request)
      .subscribe(loginRequest => {
        console.log("--[AppModelStoreService.backendExchangeAuthorization]> Receiving loginRequest: " + JSON.stringify(loginRequest));
        // The received request has two elements. The authorization token to be used and the Pilot public info.
        let token = loginRequest.authorizationToken;
        let pilotData = loginRequest.pilotPublicData;
        // Store this data on the local storage to be used along all the session.


        // The data received is a single item not a list. Convert to list to process it.
        // let list = [];
        // list.push(neocomSession);
        // TODO - Review the processing for the received data.
        // let token++9** = this.transformRequestOutput(loginRequest) as Credential;
        // Authorization completed. Go to the initial application page. Dashboard.
        this.router.navigate(['dashboard']);
      });
    // });
  }

  //--- E X C E P T I O N   M A N A G E M E N T
  public registerException(_componentName: string, _message: string, _redirection?: string): void {
    console.log("E [AppStoreService.registerException]> C: " + _componentName +
      ' M: ' + _message);
    try {
      if (null != _redirection) this.router.navigate([_redirection]);
    } catch (exception) {
      this.router.navigate(['login']);
    }
  }

  //---  H T T P   W R A P P E R S
  // public wrapCachedHttpGETCall(_request: string, _cacheGroup?: string): Observable<any> {
  //   let getCall = this.wrapHttpGETCall(_request);
  //   if(null==_cacheGroup)_cacheGroup= '-DEFAULT-';
  //   let delayType = 'none';
  //   let delay = this.cacheTimes[_cacheGroup];
  //   if (null == delay) delay = 60 * 2;
  //   return this.cache.loadFromDelayedObservable(_request, getCall, _cacheGroup, delay, delayType)
  // }
  public wrapHttpRESOURCECall(request: string): Observable<any> {
    console.log("><[AppStoreService.wrapHttpGETCall]> request: " + request);
    return this.http.get(request);
  }
  /**
   * This method wrapts the HTTP access to the backend. It should add any predefined headers, any request specific headers and will also deal with mock data.
   * Mock data comes now into two flavours. he first one will search for the request on the list of defined requests (if the mock is active). If found then it will check if the request should be sent to the file system ot it should be resolved by accessing the LocalStorage.
   * @param  request [description]
   * @return         [description]
   */
  public wrapHttpGETCall(request: string, _requestHeaders?: HttpHeaders): Observable<any> {
    // Check if we should use mock data.
    if (this.getMockStatus()) {
      // Search for the request at the mock map.
      let hit = this.responseTable[request];
      if (null != hit) {
        // Check if the resolution should be from the LocalStorage. URL should start with LOCALSTORAGE::.
        // if (hit.search('LOCALSTORAGE::') > -1) {
        //   return Observable.create((observer) => {
        //     try {
        //       let targetData = this.storage.get(hit + ':' + this.accessCredential().getId());
        //       if (null != targetData) {
        //         // .then((data) => {
        //         console.log('--[AppStoreService.wrapHttpGETCall]> Mockup data: ', targetData);
        //         // Process and convert the data string to the class instances.
        //         let results = this.transformRequestOutput(JSON.parse(targetData));
        //         observer.next(results);
        //         observer.complete();
        //       } else {
        //         observer.next([]);
        //         observer.complete();
        //       }
        //     } catch (mockException) {
        //       observer.next([]);
        //       observer.complete();
        //     }
        //   });
        // } else 
        request = hit;
      }
    }
    console.log("><[AppStoreService.wrapHttpGETCall]> request: " + request);

    // Compose the headers. Use the header template, then add global headers and finally use call headers if available.
    let HEADERS = new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Access-Control-Allow-Origin', '*')
      .set('xApp-Name', environment.name)
      .set('xApp-version', environment.version)
      .set('xApp-Platform', 'Angular 6.1.x')
      .set('xApp-Brand', 'CitasCentro-Demo')
      .set('xApp-Signature', 'S0000.0011.0000');

    // Add authentication token but only for authorization required requests.
    let cred = this.accessCredential();
    if (null != cred) {
      let auth = this.accessCredential().getAuthorization();
      if (null != auth) HEADERS = HEADERS.set('xApp-Authentication', auth);
      console.log("><[AppStoreService.wrapHttpPOSTCall]> xApp-Authentication: " + auth);
    }
    if (null != _requestHeaders) {
      for (let key of _requestHeaders.keys()) {
        HEADERS = HEADERS.set(key, _requestHeaders.get(key));
      }
    }
    return this.http.get(request, { headers: HEADERS });
  }
  public wrapHttpPOSTCall(request: string, _body: string, _requestHeaders?: HttpHeaders): Observable<any> {
    // Check if we should use mock data.
    if (this.getMockStatus()) {
      // Search for the request at the mock map.
      let hit = this.responseTable[request];
      if (null != hit) {
        // Check if the resolution should be from the LocalStorage. URL should start with LOCALSTORAGE::.
        if (hit.search('LOCALSTORAGE::') > -1) {
          return Observable.create((observer) => {
            try {
              let targetData = this.storage.get(hit + ':' + this.accessCredential().getId());
              if (null != targetData) {
                // .then((data) => {
                console.log('--[AppStoreService.wrapHttpPOSTCall]> Mockup data: ', targetData);
                // Process and convert the data string to the class instances.
                let results = this.transformRequestOutput(JSON.parse(targetData));
                observer.next(results);
                observer.complete();
              } else {
                observer.next([]);
                observer.complete();
              }
            } catch (mockException) {
              observer.next([]);
              observer.complete();
            }
          });
        } else request = hit;
      }
    }
    console.log("><[AppStoreService.wrapHttpPOSTCall]> request: " + request);
    let HEADERS = new HttpHeaders()
      .set('Content-Type', 'application/json; charset=utf-8')
      .set('Access-Control-Allow-Origin', '*')
      .set('xApp-Name', environment.name)
      .set('xApp-version', environment.version)
      .set('xApp-Platform', 'Angular 6.1.x')
      .set('xApp-Brand', 'CitasCentro-Demo')
      .set('xApp-Signature', 'S0000.0011.0000');

    // Add authentication token but only for authorization required requests.
    let cred = this.accessCredential();
    if (null != cred) {
      let auth = this.accessCredential().getAuthorization();
      if (null != auth) HEADERS = HEADERS.set('xApp-Authentication', auth);
      console.log("><[AppStoreService.wrapHttpPOSTCall]> xApp-Authentication: " + auth);
    }
    if (null != _requestHeaders) {
      for (let key of _requestHeaders.keys()) {
        HEADERS = HEADERS.set(key, _requestHeaders.get(key));
      }
    }
    return this.http.post(request, _body, { headers: HEADERS });
  }

  //--- R E S P O N S E   T R A N S F O R M A T I O N
  public transformRequestOutput(entrydata: any): INode[] | INode {
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
      return this.convertNode(entrydata);
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
      // case "AppointmentReport":
      //   let reportCita = new AppointmentReport(node);
      //   console.log("--[AppModelStoreService.convertNode]> AppointmentReport node: " + reportCita.reportdate);
      //   return reportCita;
      // case "Credencial":
      //   let convertedCredential = new Perfil(node);
      //   console.log("--[AppModelStoreService.convertNode]> AppointmentReport node: " + convertedCredential.getId());
      //   return convertedCredential;
      // case "CitasTemplate":
      //   let convertedTemplate = new CitasTemplate(node);
      //   console.log("--[AppModelStoreService.convertNode]> CitasTemplate node: " + convertedTemplate.nombre);
      //   return convertedTemplate;
      // case "Limitador":
      //   let convertedLimit = new Limitador(node);
      //   console.log("--[AppModelStoreService.convertNode]> Limitador node: " + convertedLimit.titulo);
      //   return convertedLimit;
      // case "TemplateBuildingBlock":
      //   let convertedBuildingBlock = new TemplateBuildingBlock(node);
      //   console.log("--[AppModelStoreService.convertNode]> TemplateBuildingBlock node: " + convertedBuildingBlock.nombre);
      //   return convertedBuildingBlock;
      // case "ActoMedico":
      //   let convertedActoMedico = new ActoMedico(node);
      //   console.log("--[AppModelStoreService.convertNode]> ActoMedico node: " + convertedActoMedico.nombre);
      //   return convertedActoMedico;
      // case "OpenAppointment":
      //   let convertedAppointment = new OpenAppointment(node);
      //   console.log("--[AppModelStoreService.convertNode]> OpenAppointment node: " + convertedAppointment.getId());
      //   return convertedAppointment;
      // case "ServiceAuthorized":
      //   let convertedService = new ServiceAuthorized(node);
      //   console.log("--[AppModelStoreService.convertNode]> ServiceAuthorized node: " + convertedService.getId());
      //   return convertedService;
      // case "AppModule":
      //   let convertedAppModule = new AppModule(node);
      //   console.log("--[AppModelStoreService.convertNode]> AppModule node: " + convertedAppModule.nombre);
      //   return convertedAppModule;
    }
  }
}