// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { PilotAction } from './pilotaction';

export class Asset extends NeoComNode {
  public assetID: number = -1.0;
  public locationID: number = -2;
  public typeID: number = -1;
  public quantity: number = 0;
  public ownerID: number = -1;
  public name: string = "<name>";
  public category: string = "Planetary Commodities";
  public groupName: string = "Refined Commodities";
  public tech: string = "Tech I";
  public blueprint: boolean = false;
  public ship: boolean = false;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Asset";
  }
}
