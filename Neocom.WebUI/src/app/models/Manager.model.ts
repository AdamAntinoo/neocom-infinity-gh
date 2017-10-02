// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

//--- CORE
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
//import { PilotAction } from './pilotaction';
import { Region } from './Region.model';
import { Location } from './Location.model';
import { ProcessingAction } from './ProcessingAction.model';

export class Manager extends NeoComNode {
  public regions: Region[] = [];
  public locations: Location[] = [];
  public regionCount: number = 0;
  public locationCount: number = 0;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Manager";
    // Fill additional fields after the object is parsed.
    this.regionCount = Object.keys(this.regions).length;
  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    let collab = [];
    // Add myself to the list and then if expanded add all my data depending on the Variant.
    collab.push(this);

    if (this.expanded) {
      if (variant == EVariant.PILOTMANAGERS) {
        collab.push(this);
      }
    }
    return collab;
  }
}
