//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5.2.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../interfaces/EPack.enumerated';
import { ELocationType } from '../interfaces/EPack.enumerated';
import { ESeparator } from '../interfaces/EPack.enumerated';
// import { EThemeSelector } from '../interfaces/EPack.enumerated';
import { INeoComNode } from '../interfaces/INeoComNode.interface';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
// import { NeoComAsset } from '../models/NeoComAsset.model';
// import { SpaceContainer } from '../models/SpaceContainer.model';
// import { Ship } from '../models/Ship.model';
// import { Separator } from '../models/Separator.model';
import { ColorTheme } from 'app/models/ui/ColorTheme.model';

export class Location extends NeoComNode {
  // public recordid: number = -2;
  public id: number = -2;
  public stationId: number = -2;
  public station: string = "-STATION-";
  public systemId: number = -2;
  public system: string = "-SYSTEM-";
  public constellationId: number = -2;
  public constellation: string = "-CONSTELLATION";
  public regionId: number = -2;
  public region: string = "-REGION-";
  public security: number = 0.0;
  public typeId: string = "EMPTY";
  public urlLocationIcon: string;

  private _roles: string[] = [];

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Location";
    this.themeColor = new ColorTheme().setThemeColor(ESeparator.ORANGE);
    // BUG Suppresion. If the systemId is negative then copy the id.
    if (this.systemId < 0) this.systemId = this.stationId;
  }
  //--- G E T T E R S   &   S E T T E R S
  public getName(): string {
    return this.station;
  }
  public getLocationType(): ELocationType {
    let type = ELocationType["" + this.typeId];
    let typedType = ELocationType[type];
    return ELocationType[this.typeId];
  }
  public getRegion(): string {
    return this.region;
  }
  public getRegionId(): number {
    return this.regionId;
  }
  public getSystem(): string {
    return this.system;
  }
  public getSystemId(): number {
    if (this.systemId < 0) this.systemId = this.stationId;
    return this.systemId;
  }
  public getConstellation(): string {
    return this.constellation;
  }
  public getStationName(): string {
    return this.station;
  }
  public getStationId(): number {
    return this.stationId;
  }
  /**
  This method should know the internal representation of the different types of Locations and return the unique ID that represents the unique locator. If the Location is a CCP location this is a selector depending on the available values on the system, constellation or region.
  */
  public getLocationId(): number {
    if (this.typeId == "CCPLOCATION")
      return Math.max(Math.max(Math.max(this.stationId, this.systemId), this.constellationId), this.regionId);
    if (this.typeId == "UNKNOWN")
      return this.stationId;
    return this.systemId;
  }
  public getRoles(): string[] {
    return this._roles;
  }
  public setRoles(rolelist: string[]): Location {
    this._roles = rolelist;
    return this;
  }
}
