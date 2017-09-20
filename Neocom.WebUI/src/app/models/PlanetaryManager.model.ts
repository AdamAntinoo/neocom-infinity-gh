// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { PilotAction } from './pilotaction';
import { Region } from './Region.model';
import { Manager } from './Manager.model';
import { Location } from './Location.model';
import { Render } from './Render.model';

export class PlanetaryManager extends Manager {
  //  public jsonClass: string = "Manager";
  // public regions: Region[] = [];
  // public locations: Location[] = [];

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "PlanetaryManager";
    this.locations = this.extractLocations();
    this.locationCount = this.locations.length;
    // // Convert the internal structures to their own class instances.
    // let construction = [];
    // let test = this.regions;
    // for (let key of this.regions) {
    //   let region = this.regions[key];
    //   let regClass = new Region(region);
    //   construction.push(regClass);
    // }
    // this.regions = construction;
  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    // Initialize the list to be output.
    let collab: NeoComNode[] = [];
    let rootlist: NeoComNode[] = [];
    // Check the variant and return the list depending on it.
    if (variant == EVariant.PLANETARYMANAGER) {
      // Check the size of the Region list and is small then use the list of Locations.
      if (this.regionCount < 4)
        rootlist = this.locations;
      else
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
  private extractLocations(): Location[] {
    let locs = [];
    for (let key of Object.keys(this.regions)) {
      let region = this.regions[key];
      if (key != "-1") {
        let lochash = this.regions[key].children;
        for (let lockey of Object.keys(lochash)) {
          locs.push(new Location(lochash[lockey]));
        }
        this.locationCount = this.locations.length;
      }
    }
    return locs;
  }
}
