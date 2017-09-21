//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { Asset } from '../models/Asset.model';
import { Separator } from '../models/Separator.model';
import { Resource } from '../models/Resource.model';
import { PlanetaryTransformation } from '../models/PlanetaryTransformation.model';

export class ProcessingAction extends NeoComNode {
  public resources: Resource[] = [];
  public actions: PlanetaryTransformation[] = [];
  public inputs: Resource[] = [];
  public outputs: Resource[] = [];
  public runs: number = 233;
  public actionResults = [];
  public targetId = -1;
  // private totalValueCalculated: number = -1;
  // private totalVolumeCalculated: number = -1;
  //
  // public location: string = "LOCATION";
  // public position: string;
  // public children = [];
  // public stackCount: number = 0;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "ProcessingAction";

    // Process the downloaded data into our understandable properties.
    this.inputs = [];
    for (let key in this.resources) {
      this.inputs.push(new Resource(this.resources[key]));
    }
    this.outputs = [];
    for (let key in this.actionResults) {
      let res = new Resource(this.actionResults[key]);
      this.outputs.push(res);
      // Update the target flag.
      if (res.typeID == this.targetId) res.activateTarget();
    }

    // Calculate the toal value of this location contents.
    // this.totalValueCalculated = 0;
    //   this.totalValueCalculated += asset.item.baseprice * asset.quantity;
    //   this.totalVolumeCalculated += asset.item.volume * asset.quantity;
    // }
    // this.stackCount = this.children.length;
    // this.outputs.push(new Resource({ id: 2389, name: "Plasmoids", quantity: 100 }))
    // this.outputs.push(new Resource({ id: 3645, name: "Water", quantity: 0 }))
    // this.outputs.push(new Resource({ id: 3645, name: "Superconductors", quantity: 165 }))
  }

  // public collaborate2View(variant: EVariant): NeoComNode[] {
  //   let collab = [];
  //   // If the node is expanded then add its assets.
  //   if (this.expanded) {
  //     collab.push(new Separator());
  //     collab.push(this);
  //     // Process each item at the rootlist for more collaborations.
  //     for (let node of this.children) {
  //       if (node.jsonClass == "NeoComAsset") {
  //         let asset = new Asset(node)
  //         let partialcollab = asset.collaborate2View(variant);
  //         for (let partialnode of partialcollab) {
  //           collab.push(partialnode);
  //         }
  //       }
  //     }
  //     collab.push(new Separator());
  //   } else collab.push(this);
  //   return collab;
  // }
}
