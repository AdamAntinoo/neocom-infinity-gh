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
import { Manager } from './Manager.model';
import { PilotAction } from './pilotaction';

export class NeoComCharacter extends NeoComNode {
  private _downloaded: boolean = false;
  private downloading: boolean = false;
  private _managerList: Manager[] = null;

  public characterID: number = -1.0;
  public active: boolean = true;
  public accountBalance: number = -1.0;
  public urlforAvatar: string = "http://image.eveonline.com/character/92223647_256.jpg";
  public lastKnownLocation: string = "- HOME -";
  public name: string = "<name>";
  public actions: PilotAction[] = [];
  public corporation: boolean = false;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Pilot";
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
}
