//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { Asset } from '../models/Asset.model';

export class Location extends NeoComNode {
  public location: string = "LOCATION";
  public position: string;
  public children = [];

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Location";
  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    let collab = [];
    collab.push(this);
    // If the node is expanded then add its assets.
    if (this.expanded) {
      // Process each item at the rootlist for more collaborations.
      for (let node of this.children) {
        if (node.jsonClass == "NeoComAsset") {
          let asset = new Asset(node)
          let partialcollab = asset.collaborate2View(variant);
          for (let partialnode of partialcollab) {
            collab.push(partialnode);
          }
        }
      }
    }
    return collab;
  }
}
