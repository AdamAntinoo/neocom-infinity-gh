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
// import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppStoreService } from 'app/services/appstore.service';
//--- INTERFACES
//--- MODELS
import { NeoComNode } from 'app/models/NeoComNode.model';
import { Location } from 'app/models/Location.model';
// import { NeoComAsset } from 'app/models/NeoComAsset.model';
import { Property } from 'app/models/Property.model';
import { Corporation } from 'app/models/Corporation.model';
import { Alliance } from 'app/models/Alliance.model';

export class Pilot extends NeoComNode {
  public characterId: number = -1;
  public name: string = "-NAME-";
  public accountBalance: number = -1.0;
  public urlforAvatar: string = "http://image.eveonline.com/character/92223647_256.jpg";
  public corporation: Corporation = new Corporation();
  public alliance: Alliance = new Alliance();
  public birthday: string = "";
  public securityStatus: number = 1.0;
  public locationRoles: Property[] = [];

  public lastKnownLocation: Location = new Location();
  private _locationRoles: Property[] = null;

  //--- ADDITIONAL DOWNLOAD DATA STRUCTURES
  // private _assets: NeoComAsset[] = [];
  private _assetsDownloaded: boolean = false;
  // private assetsDownloaded: boolean = false;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Pilot";
    // Transform the pilot field to a class Pilot.
    if (null != this.lastKnownLocation) {
      let newlocation = new Location(this.lastKnownLocation);
      this.lastKnownLocation = newlocation;
    }
    if (null != this.corporation) {
      let newcorp = new Corporation(this.corporation);
      this.corporation = newcorp;
    }
    if (null != this.alliance) {
      let newalliance = new Alliance(this.alliance);
      this.alliance = newalliance;
    }
    // Transform the ist of Properties.
    if (null != this.locationRoles) {
      let roles = [];
      for (let item of this.locationRoles) {
        roles.push(new Property(item));
      }
      this._locationRoles = roles;
    } else this._locationRoles = [];
  }
  //--- G E T T E R S   &   S E T T E R S
  public getId() {
    return this.characterId;
  }
  public getCharacterId() {
    return this.characterId;
  }
  public getName() {
    return this.name;
  }
  // public getAssets(): NeoComAsset[] {
  //   return this._assets;
  // }
  // public setAssets(newassets: NeoComAsset[]) {
  //   this._assets = newassets;
  //   this._assetsDownloaded = true;
  // }
  public getUrlforAvatar() {
    if (null != this.lastKnownLocation) return this.urlforAvatar;
    else return "";
  }
  public getIconUrl() {
    if (null != this.lastKnownLocation) return this.urlforAvatar;
    else return "";
  }
  public getLocationRoles(): Property[] {
    return this._locationRoles;
  }
  public setLocationRoles(roles: Property[]): Pilot {
    this._locationRoles = roles;
    return this;
  }
  public getLastKnownLocation(): Location {
    return this.lastKnownLocation;
  }
  //--- ACCESSOR TO DATA
  public ifContainsAssets(): boolean {
    // Check if the assets are already stored on the pilot.
    if (this._assetsDownloaded)
      // if (this._assets.length > 0) return true;
      return false;
  }
  // public accessAllAssets(downloadService: AppModelStoreService): Observable<NeoComAsset[]> {
  //   if (this._assetsDownloaded)
  //     return new Observable(observer => {
  //       setTimeout(() => {
  //         observer.next(this._assets);
  //       }, 100);
  //       setTimeout(() => {
  //         observer.complete();
  //       }, 100);
  //     });
  //   else {
  //     this.downloaded = true;
  //     return downloadService.getBackendPilotAssets(this.characterId);
  //   }
  // }
}
