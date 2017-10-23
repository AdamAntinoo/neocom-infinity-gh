// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { Separator } from './Separator.model';
import { Ship } from './Ship.model';
import { SpaceContainer } from './SpaceContainer.model';

export class NeoComAsset extends NeoComNode {
  public totalValueCalculated: number = -1;
  public totalVolumeCalculated: number = -1;
  public contents: NeoComAsset[] = [];

  public assetID: number = -1.0;
  public locationID: number = -2;
  public typeID: number = -1;
  public quantity: number = 0;
  public ownerID: number = -1;
  public name: string = "<name>";
  public category: string = "Planetary Commodities";
  public groupName: string = "Refined Commodities";
  public tech: string = "Tech I";
  public blueprint: boolean = false;
  public ship: boolean = false;
  public item = null;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    //  this.jsonClass = "Asset";
  }
  /**
  This method informs the view renderer that this node can be expanded. This should trigger the rendering for the expand/collapse arrow icon and its functionality.
  */
  public canBeExpanded(): boolean {
    return false;
  }
  public getName(): string {
    return this.name;
  }
  public processDownloadedAssets(assets: NeoComNode[]): NeoComAsset[] {
    let results: NeoComAsset[] = [];
    for (let node of assets) {
      switch (node.jsonClass) {
        case "NeoComAsset":
          let asset = new NeoComAsset(node);
          this.totalValueCalculated += asset.item.baseprice * asset.quantity;
          this.totalVolumeCalculated += asset.item.volume * asset.quantity;
          results.push(asset);
          break;
        case "SpaceContainer":
          let container = new SpaceContainer(node);
          // this.totalValueCalculated += container.item.baseprice * container.quantity;
          // this.totalVolumeCalculated += container.item.volume * container.quantity;
          results.push(container);
          break;
        case "Ship":
          let ship = new Ship(node);
          this.totalValueCalculated += ship.item.baseprice;
          //     this.totalVolumeCalculated += container.item.volume * container.quantity;
          results.push(ship);
          break;
      }
    }
    return results;
  }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
    let collab = [];
    // If the node is expanded then add its assets.
    if (this.expanded) {
      // Check if the contents of the Location are downloaded.
      if (this.downloaded) {
        collab.push(new Separator().setVariation(ESeparator.YELLOW));
        collab.push(this);
        // Process each item at the rootlist for more collaborations.
        // Apply the processing policies before entering the processing loop. Usually does the sort.
        let sortedContents: NeoComAsset[] = this.contents.sort((n1, n2) => {
          if (n1.getName() > n2.getName()) {
            return 1;
          }
          if (n1.getName() < n2.getName()) {
            return -1;
          }
          return 0;
        });
        for (let node of sortedContents) {
          // switch (node.jsonClass) {
          //   case "NeoComAsset":
          //     let asset = new NeoComAsset(node);
          let partialcollab = node.collaborate2View(appModelStore, variant);
          for (let partialnode of partialcollab) {
            collab.push(partialnode);
          }
          //     break;
          //   case "SpaceContainer":
          //     let container = new SpaceContainer(node);
          //     let containerCollaboration = container.collaborate2View(appModelStore, variant);
          //     for (let partialnode of containerCollaboration) {
          //       collab.push(partialnode);
          //     }
          //     break;
          // }
        }
      } else {
        // Call the backend to download the contents. On callback we need to fire an event to refresh the display.
        appModelStore.getBackendLocationsContents(this.assetID)
          .subscribe(result => {
            console.log("--[Location.collaborate2View.getBackendLocationsContents]>AssetList: " + result.length);
            // The the list of assets contained at the location.
            // Process them to upgrade to expandable types and also to account for their volume and price.
            this.contents = this.processDownloadedAssets(result);
            this.downloaded = true;
            appModelStore.fireRefresh();
          });
        // Add an spinner to the output to inform the user of the background task.
        collab.push(new Separator().setVariation(ESeparator.SPINNER));
      }
      collab.push(new Separator().setVariation(ESeparator.YELLOW));
    } else collab.push(this);
    return collab;
  }
}
