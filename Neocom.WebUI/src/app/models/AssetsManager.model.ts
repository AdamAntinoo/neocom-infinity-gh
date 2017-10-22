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
  /**
  Generates the list of nodes that should be rendered depending on the Manager state.
  */
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
    let collab = [];
    // Sort the list of Characters before processing their collaborations.
    let sortedRegions = this.sortRegions(this.regions);
    // Add all the Regions that are the first representation level.
    for (let reg of sortedRegions) {
      // Each of the nodes should have the possibility to add their own collaboration nodes.
      let collaboration = reg.collaborate2View(appModelStore, variant);
      for (let node of collaboration) {
        collab.push(node);
      }
    }
    return collab;
  }
  private sortRegions(nodeList: Region[]): Region[] {
    let sortedContents: Region[] = nodeList.sort((n1, n2) => {
      if (n1.getName() > n2.getName()) {
        return 1;
      }
      if (n1.getName() < n2.getName()) {
        return -1;
      }
      return 0;
    });
    return sortedContents;
  }
}
