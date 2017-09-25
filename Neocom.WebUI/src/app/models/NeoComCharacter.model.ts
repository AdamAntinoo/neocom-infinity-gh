// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { Login } from './Login.model';
import { Manager } from './Manager.model';
import { PilotAction } from './pilotaction';

export class NeoComCharacter extends NeoComNode {
  private _downloaded: boolean = false;
  //  private downloading: boolean = false;
  private _managerList: Manager[] = null;
  private _assetsManager: Manager = null;
  private _planetaryManager: Manager = null;

  public characterID: number = -1.0;
  public active: boolean = true;
  public accountBalance: number = -1.0;
  public urlforAvatar: string = "http://image.eveonline.com/character/92223647_256.jpg";
  public lastKnownLocation: string = "- HOME -";
  public name: string = "<name>";
  public corporation: boolean = false;
  public loginParent: Login = null;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    // Process the Managers if available to store thir respective instances at the right place.
    //    this._managerList = this.processManagers();
    this.jsonClass = "NeoComCharacter";
  }

  public getId() {
    return this.characterID;
  }
  public getName() {
    return this.name;
  }
  public setName(newname: string) {
    this.name = newname;
  }
  public getUrlforAvatar() {
    return this.urlforAvatar;
  }
  /**
  Sets the link to the parent so we can keep the chain form the deepest element to the head of the Login. There is no other easy way to gett all that information prepared for the links.
  */
  public setLoginReference(ref: Login): void {
    this.loginParent = ref;
  }
  public getLoginRefId(): string {
    if (null != this.loginParent) return this.loginParent.getLoginId();
    else return "-";
  }
  /**
  Get access to the store list of Managers. If this list has not been doanloaded already then we use the Service to go to the backend server to retieve that list.
  */
  public accessPilotManagers(downloadService: AppModelStoreService): Observable<Manager[]> {
    if (this._downloaded)
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this._managerList);
        }, 100);
        setTimeout(() => {
          observer.complete();
        }, 100);
      });
    else {
      this._downloaded = true;
      return downloadService.getBackendPilotManagerList(this.getId());
    }
  }
  public accessPlanetaryManager(downloadService: AppModelStoreService): Observable<Manager> {
    if (this._downloaded)
      return new Observable(observer => {
        setTimeout(() => {
          // Search for the PLanetary Manager
          //    if (null == this._planetaryManager) {
          //    this._managerList = this.processManagers();
          for (let manager of this._managerList) {
            if (manager.jsonClass == "PlanetaryManager") {
              // let managers = [];
              // managers.push(manager);
              observer.next(manager);
              //  return;
            }
          }
          //    observer.next(null);
          // If we reach this point we have not found the manager.
          //  throw new TypeError("Planetary Manager not found.");
          // } else {
          //   let managers = [];
          //   managers.push(this._planetaryManager);
          //   observer.next(managers);
          // }
        }, 100);
        setTimeout(() => {
          observer.complete();
        }, 100);
      });
    else {
      this._downloaded = true;
      downloadService.getBackendPilotManagerList(this.getId());
      // .subscribe(result => {
      //   this._managerList = result;
      //   return this.accessPlanetaryManager(downloadService);
      // });
    }
  }
  public storePilotManagers(managers: Manager[]): void {
    this._managerList = managers;
  }
  public setPlanetaryManager(manager: Manager): Manager {
    this._planetaryManager = manager;
    return this._planetaryManager;
  }
  private processManagers(): Manager[] {
    let managers = [];
    for (let manager of this._managerList) {
      managers.push(manager);
      switch (manager.jsonClass) {
        case "AssetsManager":
          this._assetsManager = manager;
          break
        case "Planetary":
          this._planetaryManager = manager;
          break
      }

      return managers;
    }
  }
}
