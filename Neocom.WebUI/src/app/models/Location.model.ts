//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { Asset } from '../models/Asset.model';
import { Separator } from '../models/Separator.model';

export class Location extends NeoComNode {
  private totalValueCalculated: number = -1;
  private totalVolumeCalculated: number = -1;

  public location: string = "LOCATION";
  public position: string;
  public children = [];
  public stackCount: number = 0;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Location";
    // Calculate the toal value of this location contents.
    this.totalValueCalculated = 0;
    this.totalVolumeCalculated = 0;
    for (let asset of this.children) {
      this.totalValueCalculated += asset.item.baseprice * asset.quantity;
      this.totalVolumeCalculated += asset.item.volume * asset.quantity;
    }
    this.stackCount = this.children.length;
  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    let collab = [];
    // If the node is expanded then add its assets.
    if (this.expanded) {
      collab.push(new Separator());
      collab.push(this);
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
      collab.push(new Separator());
    } else collab.push(this);
    return collab;
  }
}
