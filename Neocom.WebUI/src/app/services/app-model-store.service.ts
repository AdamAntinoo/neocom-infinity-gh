// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - Service

/**
This service will store persistent application data and has the knowledge to get to the backend to retrieve any data it is requested to render on the view.
*/

import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { Router } from '@angular/router';

//--- HTTP PACKAGE
import { Http } from '@angular/http';
import { Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- SERVICES
//--- INTERFACES
import { IDataSource } from '../classes/IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from '../classes/DataSourceLocator';
//--- MODELS
import { Login } from '../models/Login.model';
import { Render } from '../models/Render.model';
import { Pilot } from '../models/Pilot.model';
import { Corporation } from '../models/Corporation.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { NeoComCharacter } from '../models/NeoComCharacter.model';
import { Manager } from '../models/Manager.model';
import { AssetsManager } from '../models/AssetsManager.model';
import { PlanetaryManager } from '../models/PlanetaryManager.model';
import { ProcessingAction } from '../models/ProcessingAction.model';
import { Separator } from '../models/Separator.model';

@Injectable()
export class AppModelStoreService {
  static APPLICATION_NAME: string = "NeoCom";
  static APPLICATION_VERSION: string = "v 0.2.0 - STABLE"
  static APPLICATION_SERVICE_PORT = "9000";
  static RESOURCE_SERVICE_URL: string = "http://localhost:" + AppModelStoreService.APPLICATION_SERVICE_PORT + "/api/v1";

  private _loginList: Login[] = null; // List of Login structures to be used to aggregate Keys
  private _currentLogin: Login = null; // The current Login active.
  private _currentCharacter: NeoComCharacter = null; // The current active character

  //private _dataSourceCache: IDataSource[] = [];
  //private _activeDataSource: IDataSource = null;
  private _viewList: Observable<Array<Render>>;

  constructor(private http: Http, private router: Router) { }

  //--- C O M M O N    C A L L S
  public getApplicationName(): string {
    return AppModelStoreService.APPLICATION_NAME;
  }
  public getApplicationVersion(): string {
    return AppModelStoreService.APPLICATION_VERSION;
  }

  //--- B A C K E N D    C A L L S
  /**
  Go to the backend Database to retrieve the list of declared Logins to let the user to select the one he/she wants for working. If the list is already downloaded then do not access again the Database and return the cached list.
  */
  public getBackendLoginList(): Observable<Login[]> {
    console.log("><[AppModelStoreService.getBackendLoginList]");
    let request = AppModelStoreService.RESOURCE_SERVICE_URL + "/loginlist";
    return this.http.get(request)
      .map(res => res.json())
      .map(result => {
        console.log("--[AppModelStoreService.getBackendLoginList]> Processing response.");
        // Process the result into a set of Logins or process the Error Message if so.
        let constructionList: Login[] = [];
        // Process the resulting hash array into a list of Logins.
        for (let key in result) {
          // Access the object into the spot.
          let node = result[key];
          // Check that we have an Action on the spot.
          if (node.jsonClass == "Login") {
            let convertedLogin = new Login(node);
            console.log("--[AppModelStoreService.getBackendLoginList]> Identified Login node: " + convertedLogin.getLoginId());
            constructionList.push(convertedLogin);
          }
        }
        this._loginList = constructionList
        console.log("<<[AppModelStoreService.getBackendLoginList]> Processed: " + this._loginList.length);
        return this._loginList;
      });
  }
  /**
  The initial version only reported the Managers but seems more effective to retieve the complete Character with theis Managers initialized.
  */
  public getBackendPilotDetailed(loginid: string, characterid: number): Observable<NeoComCharacter> {
    console.log("><[AppModelStoreService.getBackendPilotManagerList]>Characterid = " + characterid);
    //  let loginid = this.accessLogin().getLoginId();
    return this.http.get(AppModelStoreService.RESOURCE_SERVICE_URL + "/login/" + loginid + "/pilot/" + characterid)
      .map(res => res.json())
      .map(result => {
        let newnode = null;
        switch (result.jsonClass) {
          case "Corporation":
            newnode = new Corporation(result);
            break;
          case "Pilot":
            newnode = new Pilot(result);
            break;
          default:
            newnode = result;
            break;
        }
        return newnode;
      });
  }
  public getBackendPilotManagers(loginid: string, characterid: number): Observable<Manager[]> {
    return this.http.get(AppModelStoreService.RESOURCE_SERVICE_URL + "/login/" + loginid + "/pilot/" + characterid + "/pilotmanagers")
      .map(res => res.json())
      .map(result => {
        let managerList = [];
        for (let manager of result) {
          let newman = null;
          switch (manager.jsonClass) {
            case "AssetsManager":
              newman = new AssetsManager(manager);
              break;
            case "PlanetaryManager":
              newman = new PlanetaryManager(manager);
              break;
            default:
              newman = new Manager(manager);
              break;
          }
          managerList.push(newman);
        }
        return managerList;
      });
  }
  public getBackendPilotPlanetaryManager(characterid: number): Observable<Manager> {
    console.log("><[AppModelStoreService.getBackendPilotManagerList]>Characterid = " + characterid);
    let loginid = this.accessLogin().getLoginId();
    return this.http.get(AppModelStoreService.RESOURCE_SERVICE_URL + "/login/" + loginid + "/pilot/" + characterid + "/planetarymanager")
      .map(res => res.json())
      .map(result => {
        if (result.jsonClass == "PlanetaryManager") {
          let manager = new PlanetaryManager(result);
          return manager;
        } else return result;
      });
  }
  public getBackendPlanetaryOptimizedScenario(locid: number): Observable<ProcessingAction[]> {
    console.log("><[AppModelStoreService.getBackendPilotRoaster]>Loginid = " + locid);
    // Get the current Login identifier and the current Character identifier to be used on the HTTP request.
    let loginid = this._currentLogin.getLoginId();
    let characterid = this._currentCharacter.getId();
    //  this.cookieService.put("login-id", "default")
    let request = AppModelStoreService.RESOURCE_SERVICE_URL + "/login/" + loginid;
    request += "/pilot/" + characterid;
    request += "/planetarymanager/location/" + locid + "/optimizeprocess";
    return this.http.get(request)
      .map(res => res.json())
      .map(result => {
        let actionList: any[] = [];
        // Process the resulting hash array into a list of ProcessingActions.
        for (let key in result) {
          // Access the object into the spot.
          let action = result[key];
          // Check that we have an Action on the spot.
          if (action.jsonClass == "ProcessingAction") {
            let convertedAction = new ProcessingAction(action);
            actionList.push(convertedAction);
            actionList.push(new Separator());
          }
        }
        return actionList;
      });
  }
  public getBackendPilotAssetsManager(characterid: number): Observable<Manager> {
    console.log("><[AppModelStoreService.getBackendPilotAssetsManager]>Characterid = " + characterid);
    let loginid = this.accessLogin().getLoginId();
    return this.http.get(AppModelStoreService.RESOURCE_SERVICE_URL + "/login/" + loginid + "/pilot/" + characterid + "/assetsmanager")
      .map(res => res.json())
      .map(result => {
        if (result.jsonClass == "AssetsManager") {
          let manager = new AssetsManager(result);
          return manager;
        } else return result;
      });
  }


  //--- L O G I N    S E C T I O N
  public accessLoginList(): Observable<Login[]> {
    console.log("><[AppModelStoreService.accessLoginList]");
    if (null == this._loginList) {
      // Get the list form the backend Database.
      return this.getBackendLoginList();
    } else
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this._loginList);
        }, 500);
        setTimeout(() => {
          observer.complete();
        }, 500);
      });
  }
  public setLoginList(newlist: Login[]): Login[] {
    this._loginList = newlist;
    return this._loginList;
  }
  /**
  This methos was recursive that seemed to generate some inconsistencies. Removed.
  */
  public activateLoginById(newloginid: string): Observable<Login> {
    console.log("><[AppModelStoreService.activateLoginById]");
    if (null == this._loginList) {
      // We have run all the list and we have not found any Login with the right id. We should trigger an exception.
      throw new TypeError("Login identifier " + newloginid + " not found. Cannot select that login");
    }
    // We are sure that the list is present.
    // Search for the parameter login id.
    for (let lg of this._loginList) {
      if (lg.getLoginId() == newloginid) {
        this._currentLogin = lg;
        return new Observable(observer => {
          setTimeout(() => {
            observer.next(this._currentLogin);
          }, 500);
          setTimeout(() => {
            observer.complete();
          }, 500);
        });
      }
    }
  }
  // public activateLoginById(newloginid: string): Observable<Login> {
  //   console.log("><[AppModelStoreService.activateLoginById]");
  //   if (null == this._loginList) {
  //     this.accessLoginList()
  //       .subscribe(result => {
  //         console.log("--[AppModelStoreService.activateLoginById.accessLoginList]>");
  //         // Put the resulting list on the structure and reenter recursively to continue the processing.
  //         this._loginList = result;
  //         // Search for the parameter login id.
  //         for (let lg of this._loginList) {
  //           if (lg.getLoginId() == newloginid) {
  //             this._currentLogin = lg;
  //             return new Observable(observer => {
  //               setTimeout(() => {
  //                 observer.next(this._currentLogin);
  //               }, 500);
  //               setTimeout(() => {
  //                 observer.complete();
  //               }, 500);
  //             });
  //           }
  //         }
  //       });
  //   } else {
  //     // We are sure that the list is present.
  //     // Search for the parameter login id.
  //     for (let lg of this._loginList) {
  //       if (lg.getLoginId() == newloginid) {
  //         this._currentLogin = lg;
  //         return new Observable(observer => {
  //           setTimeout(() => {
  //             observer.next(this._currentLogin);
  //           }, 500);
  //           setTimeout(() => {
  //             observer.complete();
  //           }, 500);
  //         });
  //       }
  //     }
  //     // We have run all the list and we have not found any Login with the right id. We should trigger an exception.
  //     //  throw new TypeError("Login identifier " + newloginid + " not found. Cannot select that login");
  //   }
  // }
  /**
  Sets the new login that comes from the URL when the user selects one from the list of logins.
  If the Login set is different from the current Login then we fire the download of
  the list of Pilots associated with that Login's Keys.
  */
  public accessLoginById(newloginid: string): Login {
    // Check if the Login is not set.
    if (null == this._currentLogin) this._currentLogin = this.setLoginById(newloginid);
    // Check if the required login is already the active Login.
    if (this._currentLogin.getLoginId() == newloginid) return this._currentLogin;
    else return this.setLoginById(newloginid);
  }
  public setLoginById(newloginid: string): Login {
    // WARNING. This method can fail if the list is empty because of the asynch of the backend.
    if (null == this._loginList) {
      this.getBackendLoginList()
        .subscribe(result => {
          console.log("--[AppModelStoreService.accessLoginById.getBackendLoginList]>LoginList: " + JSON.stringify(result));
          // The the list of planetary resource lists to the data returned.
          this._loginList = result;
        });
    }
    // search on the list of Logins the one with the same id.
    for (let lg of this._loginList) {
      if (lg.getLoginId() == newloginid) {
        this._currentLogin = lg;
        return this._currentLogin;
      }
    }
    // We have run all the list and we have not found any Login with the right id. We should trigger an exception.
    throw new TypeError("Login identifier " + newloginid + " not found. Cannot select that login");
  }
  public accessLogin(): Login {
    return this._currentLogin;
  }

  //--- P I L O T   S E C T I O N
  /**
  Sets the current Pilot selected to the identifier received as a parameter. The selection requires the search for the character on the list of Pilots that should be related to the current Login. This starts to require the hierarchical model storage on the Service.
  */
  public setPilotById(id: number): NeoComCharacter {
    if (null != this._currentLogin) {
      this._currentCharacter = this._currentLogin.accessCharacterById(id);
    }
    if (null == this._currentCharacter) {
      this.router.navigate(['/login', this.accessLogin().getLoginId(), 'pilotroaster']);
    }
    return this._currentCharacter;
  }
  /**
  Selects the current Pilot. If this value is not set then moves the page pointer to the current Login Pilot Roaster.
  */
  public accessCharacter(): NeoComCharacter {
    if (null == this._currentCharacter) {
      // Move to the Login Pilot Roarter page.
      this.router.navigate(['/login', this.accessLogin().getLoginId(), 'pilotroaster']);
    } else return this._currentCharacter;
  }
  /**
  We asume that the current Login is setup and the we get the pilot list of the pilots associated to the keys assigned to that Login. If that data is not already downloaded then we should go to the backend services and get the list of Characters from the backend database.
  */
  // public accessPilotRoaster() {
  //   if (null != this.currentLogin) {
  //     this.currentLogin.accessPilotRoaster();
  //   } else new TypeError("Current login is null. Cannot select that login");
  // }
  public getBackendPilotRoaster(loginid: string): Observable<NeoComCharacter[]> {
    console.log("><[AppModelStoreService.getBackendPilotRoaster]>Loginid = " + loginid);
    //  this.cookieService.put("login-id", "default")
    return this.http.get(AppModelStoreService.RESOURCE_SERVICE_URL + "/login/" + loginid + "/pilotroaster")
      .map(res => res.json())
      .map(result => {
        let roaster: NeoComCharacter[] = [];
        for (let character of result) {
          // Check the differentiation between Pilot and Corporation.
          let newchar = null;
          if (character.corporation) {
            newchar = new Corporation(character);
          } else {
            newchar = new Pilot(character);
          }
          roaster.push(newchar);
        }
        // Before returning the data set it to the Model hierarchy.
        this._currentLogin.setPilotRoaster(roaster);
        return roaster;
      });
  }

  // public accessDataSource(): IDataSource {
  //   return this._activeDataSource;
  // }
  // public searchDataSource(locator: DataSourceLocator): IDataSource {
  //   let target = this._dataSourceCache[locator.getLocator()];
  //   return target;
  // }



  /**
  Checks if this datasource is already present at the registration list. If found returns the already found datasource, otherwise adds this to the list of caches datasources.
*/
  // public registerDataSource(ds: IDataSource): IDataSource {
  //   let locator = ds.getLocator();
  //   let target = this._dataSourceCache[locator.getLocator()];
  //   if (target == null) {
  //     this._dataSourceCache.push(ds);
  //     return ds;
  //   }
  //   return target;
  // }
  // public setActiveDataSource(ds: IDataSource): IDataSource {
  //   this._activeDataSource = ds;
  //   return this._activeDataSource;
  // }
}
