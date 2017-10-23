//--- INTERFACES
//import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
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
    //    this.jsonClass = "ProcessingAction";

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
}
