//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { Location } from '../models/Location.model';
import { Separator } from '../models/Separator.model';

export class Region extends NeoComNode {
  //  public region: string = "Region";
  public title: string = "-REGION-";
  public locations: Location[] = [];
  public locationCount: number = 0;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Region";
    this.locations = this.extractLocations();
  }
  public addLocation(newlocation: Location) {
    this.locations.push(newlocation);
  }
  private extractLocations(): Location[] {
    let locs = [];
    //    this.locationCount=0;
    //  for (let key of Object.keys(this.regions)) {
    //    let region = this.regions[key];
    //  if (key != "-1") {
    //      let lochash = this.regions[key].locations;
    for (let lockey of Object.keys(this.locations)) {
      locs.push(new Location(this.locations[lockey]));
    }
    this.locationCount = this.locations.length;
    //    }
    //  }
    return locs;
  }
  // public collaborate2View(variant: EVariant): NeoComNode[] {
  //    let collab = [];
  //    collab.push(this);
  //    return collab;
  //  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    // Initialize the list to be output.
    let collab: NeoComNode[] = [];
    //   let rootlist: NeoComNode[] = [];
    // Check the variant and return the list depending on it.
    //   if (variant == EVariant.PLANETARYMANAGER) {
    // Check the size of the Region list and is small then use the list of Locations.
    // if (this.regionCount < 4)
    //   rootlist = this.locations;
    // else
    //     rootlist = this.regions;
    // Check if the Region is expanded or not.
    if (this.expanded) {
      collab.push(new Separator().setVariation(ESeparator.GREEN));
      collab.push(this);
      // Process each Location for new collaborations.
      for (let node of this.locations) {
        let partialcollab = [];
        if (node.jsonClass == "Location")
          partialcollab = node.collaborate2View(variant);
        else {
          let loc = new Location(node);
          partialcollab = node.collaborate2View(variant);
        }
        for (let partialnode of partialcollab) {
          collab.push(partialnode);
        }
      }
      collab.push(new Separator().setVariation(ESeparator.GREEN));
    } else collab.push(this);
    return collab;
  }
}
