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
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { INeoComNode } from '../classes/INeoComNode.interface';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { Login } from './Login.model';
import { Manager } from './Manager.model';
import { AssetsManager } from './AssetsManager.model';
import { PlanetaryManager } from './PlanetaryManager.model';

export class NeoComCharacter extends NeoComNode {
  private _downloaded: boolean = false;
  private _managerList: Manager[] = null;
  private _assetsManager: AssetsManager = null;
  private _planetaryManager: PlanetaryManager = null;

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

  //---  G E T T E R S   A N D   S E T T E R S
  public getId() {
    return this.characterID;
  }
  public getCharacterId() {
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
  public getTotalAssets(): number {
    if (null != this._assetsManager) return this._assetsManager.totalAssets;
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

  //--- F U N C T I O N A L    A C C E S O R S
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
  public accessPilotManagers(downloadService: AppModelStoreService): Observable<Manager[]> {
    // Check we are connected to the Login.
    if (null == this.loginParent) throw new TypeError("Pilot not connected to parent Login.");
    // Check if the managers are already available.
    if (null == this._managerList) {
      return downloadService.getBackendPilotManagers(this.loginParent.getLoginId(), this.getId())
        .map(result => {
          // The the list of pilot managers that should be stored at the pilot.
          //    let man: any = null;
          this._managerList = [];
          for (let manager of result) {
            switch (manager.jsonClass) {
              case "AssetsManager":
                this.setAssetsManager(<AssetsManager>manager);
                this._managerList.push(manager);
                break;
              case "PlanetaryManager":
                this.setPlanetaryManager(<PlanetaryManager>manager);
                this._managerList.push(manager);
                break;
            }
          }
          return result;
        });
    } else {
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this._managerList);
        }, 500);
        setTimeout(() => {
          observer.complete();
        }, 500);
      });
    }
  }
  public accessAssetsManager(downloadService: AppModelStoreService): Observable<Manager> {
    if (null == this._assetsManager)
      return downloadService.getBackendPilotAssetsManager(this.getId());
    else {
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this._assetsManager);
        }, 500);
        setTimeout(() => {
          observer.complete();
        }, 500);
      });
    }
  }
  public getAssetsManager(): Manager {
    return this._assetsManager;
  }
  public setAssetsManager(newassets: AssetsManager): void {
    this._assetsManager = newassets;
  }
  public accessPlanetaryManager(downloadService: AppModelStoreService): Observable<Manager> {
    if (null == this._planetaryManager)
      return downloadService.getBackendPilotPlanetaryManager(this.getId());
    else {
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this._planetaryManager);
        }, 500);
        setTimeout(() => {
          observer.complete();
        }, 500);
      });
    }
  }
  public setPlanetaryManager(newplanetary: PlanetaryManager): void {
    this._planetaryManager = newplanetary;
  }

  // --- I N T E R F A C E   M E T H O D S
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): Manager[] {
    let collab: Manager[] = [];
    for (let manager of this._managerList) {
      collab.push(manager)
    }
    return collab;
  }
}
