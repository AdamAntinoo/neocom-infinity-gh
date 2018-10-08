//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5.2.0
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
import { HttpHeaders } from '@angular/common/http';
//--- ROUTING
// import { Router, ActivatedRoute, ParamMap } from '@angular/router';
//--- ENVIRONMENT
import { environment } from '../../environments/environment';
//--- HTTP PACKAGE
// import { Http } from '@angular/http';
// // import { HttpClient } from '@angular/common/http';
// import { Response, Headers, RequestOptions } from '@angular/http';
// // import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
//--- SERVICES
// import { AppModelStoreServiceDefinitions } from 'app/services/app-model-store.definitions.service';
//--- INTERFACES
// import { INeoComNode } from '../interfaces/INeoComNode.interface';
import { MockUpBackendService } from 'src/shared/services/MockUpBackend.service';
// import { MockUpBackendService } from 'src/shared/services/MockUp.service';
//--- MODELS
// import { NeoComSession } from '../models/NeoComSession.model';
// import { Credential } from '../models/Credential.model';
// import { Pilot } from '../models/Pilot.model';

//--- CONSTANTS
const HEADERS = new HttpHeaders()
  .set('Content-Type', 'application/json; charset=utf-8')
  .set('Access-Control-Allow-Origin', '*')
  .set('xApp-Name', environment.name)
  .set('xApp-version', environment.version)
  .set('xApp-Platform', 'Angular 6.1')
  .set('xApp-Brand', 'NeoCom')
  .set('xApp-Signature', 'S0000.1110.0000');

@Injectable({
  providedIn: 'root'
})
export class BackendService extends MockUpBackendService {
  //--- STORE FIELDS
  protected _rsaKey = null; // RSA session key generated on the login process.
  protected _publicKey = null; // Local public key from the RSA key to be used on message decryption by backend.
  // protected _sessionIdentifier: string = "-MOCK-SESSION-ID-"; // Unique session identifier to be interchanged with backend.
  // protected _session: NeoComSession = null;
  // protected _credentialList: Credential[] = null; // List of Credential data. It also includes the Pilotv2 information.
  // protected _pilotIdentifier: number = 92002067; // Pilot identifier number for this session.

  //--- C O M M O N    C A L L S
  public myUniqueID(): string {
    return Math.random().toString(36).slice(2);
  }

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

  //--- E S I - A U T H O R I Z A T I O N   F L O W
    // - C R E D E N C I A L
    public checkCredential(credential: AuthorizationRequest): Observable<Perfil> {
      console.log("><[BackendService.checkCredentials]");
      // Construct the request to call the backend.
      let request = BackendService.BACKEND_SERVICE_URL + environment.apiVersion1 + "/credencial/validate";
      console.log("--[BackendService.backendCitaCreationProcess]> request = " + request);
      console.log("--[BackendService.backendCitaCreationProcess]> body = " + JSON.stringify(credential));
      // TODO --- Encode credentials to use Basic REST authentication.
      let plainClientCredentials: string = credential.identificador + ':' + credential.password;
      let base64ClientCredentials: string = window.btoa(plainClientCredentials);
      let totalHeaders = new HttpHeaders()
        .set('Content-Type', 'application/json; charset=utf-8')
        .set('Access-Control-Allow-Origin', '*')
        .set('xApp-Name', environment.name)
        .set('xApp-version', environment.version)
        .set('xApp-Platform', 'Angular 6.1.x')
        .set('xApp-Brand', 'CitasCentro-Demo')
        .set('xApp-Signature', 'S0000.0011.0000');
      totalHeaders.set('xApp-Authorization', "Basic " + base64ClientCredentials);
      console.log("--[BackendService.backendCitaCreationProcess]> header = " + 'xApp-Authorization: ' + "Basic " + base64ClientCredentials);
      return this.http.post<Perfil>(request, JSON.stringify(credential), { headers: totalHeaders })
        .pipe(map(data => {
          let profile = this.transformRequestOutput(data) as Perfil;
          return profile;
        }));
    }
  
  /**
  Go to the backend and use the ESI api to exchange the authorization code by the refresh token. Then also store the new credential on the database and clear the list of credentials to force a new reload the next time we need them.
  This call is not mocked up so requires the backend server to be up.

  Add to the information to sent to the backend the PublicKey to be used on this session encryption.
  */

 /**
  * Go to the backend and use the ESI api to exchange the authorization code by the refresh token. Then also store the new credential on the database and clear the list of credentials to force a new reload the next time we need them.
  This call is not mocked up so requires the backend server to be up.
  *
  * Add to the information to sent to the backend the PublicKey to be used on this session encryption.
  * 
  * @param {string} code the validation code received from the rist phase on the authorization flow.
  * @memberof BackendService
  */
 public backendExchangeAuthorization(code: string): void {
    console.log("><[AppModelStoreService.backendExchangeAuthorization]");
    // Convert the public key into an string to be sent to the back end.
    window.crypto.subtle.exportKey("jwk", this.getPublicKey())
      .then((jsonKey) => {
        let stringedPublicKey = JSON.stringify(jsonKey);
        let request = AppModelStoreServiceESILogin.RESOURCE_SERVICE_URL + "/exchangeauthorization/" + code
          + "/publickey/" + jsonKey.n;
        return this.http.get(request)
          .map(res => res.json())
          // .map(result => {
          //   console.log("><[AppModelStoreService.backendExchangeAuthorization]>Response interception.");
          //   // Check if the result is an exception. If so show it on the Notifications.
          //   // if (null != result.jsonClass) this.toastr.error(result.mesage, 'Backend Exception!');
          //   // Clear the list of credentials to force their reload.
          //   this._credentialList = null;
          //   return result;
          // })
          .subscribe(neocomSession => {
            console.log("--[AppModelStoreService.backendExchangeAuthorization]> Receiving NeoComSession: " + JSON.stringify(neocomSession));
            // The data received is a single item not a list. Convert to list to process it.
            let list = [];
            list.push(neocomSession);
            // TODO - Review the processing for the received data.
            this._credentialList = this.transformRequestOutput(list) as Credential[];
            // this.setPilotIdentifier(this._credentialList[0].accountId);
            console.log("<<[AppModelStoreService.getBackendCredentialList]> Processed: " + this._credentialList.length);

            // // Decript the pilot identifier to check of we can trust the encrytion.
            // let pilotidencrypted = neocomSessionIdentifier["pilotIdentifier"];
            // let decryptPromise = window.crypto.subtle.decrypt(
            //   {
            //     name: "RSA-OAEP",
            //     //label: Uint8Array([...]) //optional
            //   },
            //   this._rsaKey, //from generateKey or importKey above
            //   pilotidencrypted //ArrayBuffer of the data
            // );
            // decryptPromise.then((decrypted) => {
            //   //returns an ArrayBuffer containing the decrypted data
            //   console.log(new Uint8Array(decrypted));
            //   let pilotiddecrypted = decrypted;
            // });

            // Authorization completed. Go to the initial application page. Dashboard.
            this.router.navigate(['dashboard']);
          });
      });
  }
}