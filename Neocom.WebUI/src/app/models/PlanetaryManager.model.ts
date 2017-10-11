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

export class PlanetaryManager extends Manager {

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "PlanetaryManager";
    this.regions = this.extractData();
    //    this.locations = this.extractLocations();
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
  Call the backend to use that location as the input for the new Planetary Optimizer. The result should be the list of action to perform with this Location resources to optimize its Market value.
  */
  public getOptimizedScenario(locid: number, downloadService: AppModelStoreService): Observable<ProcessingAction[]> {
    return downloadService.getBackendPlanetaryOptimizedScenario(locid);
  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    // Initialize the list to be output.
    let collab: NeoComNode[] = [];
    let rootlist: NeoComNode[] = [];
    // Check the variant and return the list depending on it.
    if (variant == EVariant.PLANETARYMANAGER) {
      // Check the size of the Region list and is small then use the list of Locations.
      // if (this.regionCount < 4)
      //   rootlist = this.locations;
      // else
      rootlist = this.regions;
      // Process each item at the rootlist for more collaborations.
      for (let node of rootlist) {
        let partialcollab = node.collaborate2View(variant);
        for (let partialnode of partialcollab) {
          collab.push(partialnode);
        }
      }
    }
    return collab;
  }
  // private extractLocations(): Location[] {
  //   let locs = [];
  //   for (let key of Object.keys(this.regions)) {
  //     let region = this.regions[key];
  //     if (key != "-1") {
  //       let lochash = this.regions[key].locations;
  //       for (let lockey of Object.keys(lochash)) {
  //         locs.push(new Location(lochash[lockey]));
  //       }
  //       this.locationCount = this.locations.length;
  //     }
  //   }
  //   return locs;
  // }
}
