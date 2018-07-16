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
//--- WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
//--- SERVICES
import { AppModelStoreServiceDefinitions } from 'app/services/app-model-store.definitions.service';
//--- MODELS
import { NeoComSession } from 'app/models/ui/NeoComSession.model';

const OTHER_KEY: string = "-KEY-";

export class AppModelStoreServiceStorage extends AppModelStoreServiceDefinitions {
  //--- S T O R E
  protected _session: NeoComSession = null;
  protected _sessionIdentifier: string = "-MOCK-SESSION-ID-"; // Unique session identifier to be interchanged with backend.

  //-- S E S S I O N   M A N A G E M E N T
  /**
   * This development method will help to assure that a proper session is confgured and active. During development it will create the dummy session so there is no need to go to the backend to get start information or perform a new authorization.
   * On Production it will check if there is a valid session on the localStore if the user decided to keep authorization remembered and stored. Depending on the session validity the method will allow to continue or will reroute to the authorization request page.
   * @return [description]
   */
  public checkSessionActive(): Observable<NeoComSession> {
    if (null == this._session) {
      this.toasterService.warn("Aviso !!", 'There is not valid session. Going back to initial page!.');
      this.router.navigate(['dashboard']);
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
