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
  public item = null;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Asset";
  }
  /**
  This method informs the view renderer that this node can be expanded. This should trigger the rendering for the expand/collapse arrow icon and its functionality.
  */
  public canBeExpanded(): boolean {
    return false;
  }
}
