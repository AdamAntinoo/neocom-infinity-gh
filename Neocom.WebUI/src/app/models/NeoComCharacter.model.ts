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
import { AssetsManager } from './AssetsManager.model';
import { PlanetaryManager } from './PlanetaryManager.model';
import { PilotAction } from './pilotaction';

export class NeoComCharacter extends NeoComNode {
  private _downloaded: boolean = false;
  //  private downloading: boolean = false;
  private _managerList: Manager[] = null;
  private assetsManager: AssetsManager = null;
  private planetaryManager: PlanetaryManager = null;

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
  public getLoginReference(): Login {
    return this.loginParent;
  }
  public getLoginRefId(): string {
    if (null != this.loginParent) return this.loginParent.getLoginId();
    else return "-";
  }
  public getManagers(): Manager[] {
    let manlist = [];
    if (null != this.assetsManager) manlist.push(this.assetsManager);
    if (null != this.planetaryManager) manlist.push(this.planetaryManager);
    return manlist;
  }
  /**
  Get access to the store list of Managers. If this list has not been doanloaded already then we use the Service to go to the backend server to retieve that list.
  */
  public accessPilotDetailed(downloadService: AppModelStoreService): Observable<NeoComCharacter> {
    // Get access to the parent login information to get the ID.
    if (null != this.loginParent) {
      let loginid = this.loginParent.getLoginId();
      return downloadService.getBackendPilotDetailed(loginid, this.getId());
    } else {
      let loginid = downloadService.accessLogin().getLoginId();
      return downloadService.getBackendPilotDetailed(loginid, this.getId());
    }
  }
  public accessPlanetaryManager(downloadService: AppModelStoreService): Observable<Manager> {
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this.planetaryManager);
      }, 500);
      setTimeout(() => {
        observer.complete();
      }, 500);
    });
  }
  public accessAssetsManager(downloadService: AppModelStoreService): Observable<Manager> {
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this.assetsManager);
      }, 500);
      setTimeout(() => {
        observer.complete();
      }, 500);
    });
  }
  // public storePilotManagers(managers: Manager[]): void {
  //   this._managerList = managers;
  // }
  // public setPlanetaryManager(manager: Manager): Manager {
  //   this._planetaryManager = manager;
  //   return this._planetaryManager;
  // }
  // private processManagers(): Manager[] {
  //   let managers = [];
  //   for (let manager of this._managerList) {
  //     managers.push(manager);
  //     switch (manager.jsonClass) {
  //       case "AssetsManager":
  //         this.assetsManager = manager;
  //         break
  //       case "Planetary":
  //         this.planetaryManager = manager;
  //         break
  //     }
  //
  //     return managers;
  //   }
  // }
}
