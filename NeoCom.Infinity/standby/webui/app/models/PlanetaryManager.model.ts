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
import { INeoComNode } from '../classes/INeoComNode.interface';
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { Region } from './Region.model';
import { Manager } from './Manager.model';
import { Location } from './Location.model';
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
  // --- P U B L I C   A P I
  /**
  Call the backend to use that location as the input for the new Planetary Optimizer. The result should be the list of action to perform with this Location resources to optimize its Market value.
  */
  public getOptimizedScenario(locid: number, downloadService: AppModelStoreService): Observable<ProcessingAction[]> {
    return downloadService.getBackendPlanetaryOptimizedScenario(locid);
  }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): INeoComNode[] {
    // Initialize the list to be output.
    let collab: INeoComNode[] = [];
    let rootlist: INeoComNode[] = [];
    // Check the variant and return the list depending on it.
    if (variant == EVariant.PLANETARYMANAGER) {
      // Check the size of the Region list and is small then use the list of Locations.
      // if (this.regionCount < 4)
      //   rootlist = this.locations;
      // else
      rootlist = this.sortRegions(this.regions);
      // Process each item at the rootlist for more collaborations.
      for (let node of rootlist) {
        let partialcollab = node.collaborate2View(appModelStore, variant);
        for (let partialnode of partialcollab) {
          collab.push(partialnode);
        }
      } {
      }
    }
    return collab;
  }
  /**
  Searched for the target Location on the list of locations available on the Planetary Manager. During the instance creation and the data processing we should convert the input data to Regions and also extract the locations to another list of Locations to simplify this search.
  */
  public search4Location(id: number): Location {
    for (let loc of this.locations) {
      if (loc.getLocationId() == id)
        return loc;
    }
    return null;
  }
  private sortRegions(nodeList: Region[]): Region[] {
    let sortedContents: Region[] = nodeList.sort((n1, n2) => {
      if (n1.title > n2.title) {
        return 1;
      }
      if (n1.title < n2.title) {
        return -1;
      }
      return 0;
    });
    return sortedContents;
  }

}