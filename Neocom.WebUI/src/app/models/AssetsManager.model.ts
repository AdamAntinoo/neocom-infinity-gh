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
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { PilotAction } from './pilotaction';
import { Region } from './Region.model';
import { Manager } from './Manager.model';
import { Location } from './Location.model';
import { Render } from './Render.model';
import { ProcessingAction } from './ProcessingAction.model';

export class AssetsManager extends Manager {
  public ships: any[] = [];

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "AssetsManager";
    this.regions = this.extractData();
    this.locationCount = this.locations.length;
  }
  /**
  Transform the anonymous data downloaded from the backend into the classed data. Process the Regions.
  */
  private extractData(): Region[] {
    let regs = [];
    let locs = [];
    this.regionCount = 0;
    this.locationCount = 0;
    for (let key of Object.keys(this.regions)) {
      let region = this.regions[key];
      regs.push(new Region(region));
      this.regionCount++;
      if (key != "-1") {
        let lochash = this.regions[key].locations;
        for (let lockey of Object.keys(lochash)) {
          locs.push(new Location(lochash[lockey]));
        }
        this.locationCount++;
      }
    }
    this.regions = regs;
    this.locations = locs;
    return regs;
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
