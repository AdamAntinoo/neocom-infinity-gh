//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
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
    //    this.jsonClass = "Region";
    this.locations = this.processLocations(this.locations);
  }

  // public addLocation(newlocation: Location) {
  //   this.locations.push(newlocation);
  // }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
    // Initialize the list to be output.
    let collab: NeoComNode[] = [];
    // Check if the Region is expanded or not.
    if (this.expanded) {
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.RED");
      collab.push(new Separator().setVariation(ESeparator.RED));
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Region");
      collab.push(this);
      // Process each Location for new collaborations.
      for (let node of this.locations) {
        let partialcollab = [];
        //  if (node.jsonClass == "Location")
        partialcollab = node.collaborate2View(appModelStore, variant);
        // else {
        //   let loc = new Location(node);
        //   partialcollab = node.collaborate2View(appModelStore, variant);
        // }
        for (let partialnode of partialcollab) {
          collab.push(partialnode);
        }
      }
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.RED");
      collab.push(new Separator().setVariation(ESeparator.RED));
    } else {
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Region");
      collab.push(this);
    }
    return collab;
  }
  public getName(): string {
    return this.title;
  }
  /**
  Method to process the downloaded data and converting the anonymous json objects to the Model classes.
  */
  public processLocations(locs: any[]): Location[] {
    let results: Location[] = [];
    for (let lockey of locs) {
      // Reload the right download stae of the Locations.
      let newloc = new Location(lockey);
      newloc.setDownloadState(false);
      newloc.collapse();
      results.push(newloc);
    }
    return results;
  }
}
