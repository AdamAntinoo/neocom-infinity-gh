// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { PilotAction } from './pilotaction';
import { Asset } from './Asset.model';

export class Ship extends Asset {
  public contents = [];

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Ship";
  }
  /**
  This method informs the view renderer that this node can be expanded. This should trigger the rendering for the expand/collapse arrow icon and its functionality.
  */
  public canBeExpanded(): boolean {
    return true;
  }
  public getContents() {
    return this.contents;
  }
}
