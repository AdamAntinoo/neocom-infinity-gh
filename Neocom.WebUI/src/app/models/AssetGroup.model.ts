//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
import { INeoComNode } from '../classes/INeoComNode.interface';
//--- MODELS
import { Separator } from './Separator.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { NeoComAsset } from '../models/NeoComAsset.model';
import { SpaceContainer } from './SpaceContainer.model';
import { Ship } from './Ship.model';

export class AssetGroup extends NeoComAsset {
  public type: string = "SHIPSECTION_CARGO";

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.contents = this.processDownloadedAssets(this.contents);
    this.expanded = true;
  }
  public getGroupIcon(): string {
    switch (this.type) {
      case "SHIPSECTION_HIGH":
        return "filtericonhighslot.png";
      case "SHIPSECTION_MED":
        return "filtericonmediumslot.png";
      case "SHIPSECTION_LOW":
        return "filtericonlowslot.png";
      case "SHIPSECTION_DRONES":
        return "dronebay.png";
      case "SHIPSECTION_CARGO":
        return "cargo.png";
      case "SHIPSECTION_RIGS":
        return "filtericonrigslot.png";
    }
  }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): INeoComNode[] {
    let collab: INeoComNode[] = [];
    console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.BLUE");
    collab.push(new Separator().setVariation(ESeparator.BLUE));
    console.log(">>[Region.collaborate2View]> Collaborating: " + "AssetGroup");
    collab.push(this);
    for (let node of this.contents) {
      let partialcollab = node.collaborate2View(appModelStore, variant);
      for (let partialnode of partialcollab) {
        console.log(">>[Region.collaborate2View]> Collaborating: " + "NeoComAsset");
        collab.push(partialnode);
      }
    }
    console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.BLUE");
    collab.push(new Separator().setVariation(ESeparator.BLUE));
    return collab;
  }
  public processDownloadedAssets(assets: NeoComNode[]): NeoComAsset[] {
    let results: NeoComAsset[] = [];
    for (let node of assets) {
      switch (node.jsonClass) {
        case "NeoComAsset":
          let asset = new NeoComAsset(node);
          results.push(asset);
          break;
        case "SpaceContainer":
          let container = new SpaceContainer(node);
          results.push(container);
          break;
        case "Ship":
          let ship = new Ship(node);
          results.push(ship);
          break;
        case "AssetGroup":
          let group = new AssetGroup(node);
          results.push(group);
          break;
        default:
          break;
      }
    }
    return results;
  }
}
