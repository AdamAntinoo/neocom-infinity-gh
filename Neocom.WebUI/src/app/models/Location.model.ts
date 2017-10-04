//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { Asset } from '../models/Asset.model';
import { Separator } from '../models/Separator.model';
import { Container } from '../models/Container.model';

export class Location extends NeoComNode {
  private totalValueCalculated: number = -1;
  private totalVolumeCalculated: number = -1;

  // public location: string = "LOCATION";
  // public position: string;
  //  public id;
  public stationID;
  public systemID;
  public constellationID;
  public regionID;
  public contents = [];

  //  public children = [];
  public stackCount: number = 0;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Location";
    // Calculate the toal value of this location contents.
    this.totalValueCalculated = 0;
    this.totalVolumeCalculated = 0;
    let newassetlist = [];
    for (let asset of this.contents) {
      this.totalValueCalculated += asset.item.baseprice * asset.quantity;
      this.totalVolumeCalculated += asset.item.volume * asset.quantity;
      // Convert the assets.
      let as = new Asset(asset);
      newassetlist.push(as);
    }
    this.contents = newassetlist;
    this.stackCount = this.contents.length;
  }



  public collaborate2View(variant: EVariant): NeoComNode[] {
    let collab = [];
    // If the node is expanded then add its assets.
    if (this.expanded) {
      collab.push(new Separator());
      collab.push(this);
      // Process each item at the rootlist for more collaborations.
      for (let node of this.contents) {
        switch (node.jsonClass) {
          case "Asset":
            let asset = new Asset(node);
            let partialcollab = asset.collaborate2View(variant);
            for (let partialnode of partialcollab) {
              collab.push(partialnode);
            }
            break;
          case "Container":
            let container = new Container(node);
            let containerCollaboration = container.collaborate2View(variant);
            for (let partialnode of containerCollaboration) {
              collab.push(partialnode);
            }
            break;
        }
      }
      collab.push(new Separator());
    } else collab.push(this);
    return collab;
  }
  public getLocationId(): number {
    return Math.max(Math.max(Math.max(this.stationID, this.systemID), this.constellationID), this.regionID);
  }
}
