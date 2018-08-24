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
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { Inject } from '@angular/core';
//--- WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
//--- HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
//--- ROUTER
import { Router } from '@angular/router';
//--- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- SERVICES
import { AppModelStoreServiceDefinitions } from 'app/services/app-model-store.definitions.service';
//--- MODELS
import { NeoComSession } from 'app/models/ui/NeoComSession.model';
import { Credential } from 'app/models/Credential.model';

const OTHER_KEY: string = "-KEY-";

export class AppModelStoreServiceStorage extends AppModelStoreServiceDefinitions {
  // - S T O R A G E   K E Y S
  public static SESSION_KEY: string = "-SESSION-KEY-";

  public static CREDENTIAL_ISVALID_KEY: string = "-CREDENTIAL-ISVALID-KEY-";
  public static CREDENTIAL_KEY: string = "-CREDENTIAL-KEY-";
  public static TEMPLATE_KEY: string = "-TEMPLATE-KEY-";

  //--- S T O R E
  protected _session: NeoComSession = null;
  protected _sessionIdentifier: string = "-MOCK-SESSION-ID-"; // Unique session identifier to be interchanged with backend.

  //--- C O N S T R U C T O R
  constructor(
    @Inject(SESSION_STORAGE) protected storage: WebStorageService
    , protected http: HttpClient
    , protected router: Router
    , protected toasterService: NotificationsService) {
    super(http, router, toasterService)
  }

  //-- S E S S I O N   M A N A G E M E N T
  /**
   * This development method will help to assure that a proper session is confgured and active. During development it will create the dummy session so there is no need to go to the backend to get start information or perform a new authorization.
   * On Production it will check if there is a valid session on the localStore if the user decided to keep authorization remembered and stored. Depending on the session validity the method will allow to continue or will reroute to the authorization request page.
   * @return the valid session if exists.
   */
  public checkSessionActive(): Observable<NeoComSession> {
    // If on development and the mock is active then create a fake session.
    if (this.getMockStatus())
      return this.createMockSession();

    let session = this.readSessionData();
    if (null == session) {
      this.toasterService.warn("Warning!!", 'There is not valid session. Going back to initial page!.');
      this.router.navigate(['welcome']);
    } else this._session = session;
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this._session);
      }, 500);
      setTimeout(() => {
        observer.complete();
      }, 500);
    });
  }
  /**
   * Reads and converts the found session data rom the session storage. If the data is not found returns a null.
   * @return the session instance or null if not defined or stored.
   */
  public readSessionData(): NeoComSession {
    console.log(">>[AppModelStoreServiceStorage.readSessionData]");
    let data = this.storage.get(AppModelStoreServiceStorage.SESSION_KEY);
    if (this.isNonEmptyString(data)) {
      return new NeoComSession(JSON.parse(data));
    } else return null;
  }
  public getActivePilotIdentifier(): number {
    if (null != this._session) return this._session.getPilotIdentifier();
  }
  protected createMockSession(): Observable<NeoComSession> {
    // Create a new mock session only is not already defined.
    if (null == this._session) {
      // Create a valid Session with a Credential.
      let credential = new Credential({
        accountId: 92002067,
        accountName: "Adam Antinoo"
      });
      let session = new NeoComSession({
        sessionId: "-UNIQUE-IDENTIFIER-",
        publicKey: "-RSA-GENERATED-KEY-",
        pilotIdentifier: -1,
        credential
      });
      this._session = session;
    }
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this._session);
      }, 500);
      setTimeout(() => {
        observer.complete();
      }, 500);
    });
  }
}
