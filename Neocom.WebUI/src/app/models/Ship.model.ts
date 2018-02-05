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
import { Separator } from './Separator.model';
import { NeoComNode } from './NeoComNode.model';
import { NeoComAsset } from './NeoComAsset.model';
import { SpaceContainer } from './SpaceContainer.model';
import { AssetGroup } from './AssetGroup.model';

export class Ship extends NeoComAsset {

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.totalValueCalculated = 0;
    this.totalVolumeCalculated = 0;
    this.contents = this.processDownloadedAssets(this.contents);
  }
  /**
  This method informs the view renderer that this node can be expanded. This should trigger the rendering for the expand/collapse arrow icon and its functionality.
  */
  public canBeExpanded(): boolean {
    return true;
  }
  public getContents() {
    return this.contents;
  }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
    let collab = [];
    // If the node is expanded then add its assets.
    if (this.expanded) {
      // Check if the contents of the Location are downloaded.
      if (this.downloaded) {
        console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.YELLOW");
        collab.push(new Separator().setVariation(ESeparator.YELLOW));
        console.log(">>[Region.collaborate2View]> Collaborating: " + "Ship");
        collab.push(this);
        // Process each item at the rootlist for more collaborations.
        // Apply the processing policies before entering the processing loop. Usually does the sort.
        // let sortedContents: NeoComAsset[] = this.contents.sort((n1, n2) => {
        //   if (n1.getName() > n2.getName()) {
        //     return 1;
        //   }
        //   if (n1.getName() < n2.getName()) {
        //     return -1;
        //   }
        //   return 0;
        // });
        for (let node of this.contents) {
          let partialcollab = node.collaborate2View(appModelStore, variant);
          for (let partialnode of partialcollab) {
            console.log(">>[Region.collaborate2View]> Collaborating: " + "NeoComAsset");
            collab.push(partialnode);
          }
        }
      } else {
        // Call the backend to download the contents. On callback we need to fire an event to refresh the display.
        appModelStore.getBackendContainerContents(this.assetID)
          .subscribe(result => {
            console.log("--[Location.collaborate2View.getBackendLocationsContents]>AssetList: " + result.length);
            // The the list of assets contained at the location.
            // Process them to upgrade to expandable types and also to account for their volume and price.
            this.contents = this.processDownloadedAssets(result);
            this.downloaded = true;
            appModelStore.fireRefresh();
          });
        // Add an spinner to the output to inform the user of the background task.
        console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.SPINNER");
        collab.push(new Separator().setVariation(ESeparator.SPINNER));
      }
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Separator.YELLOW");
      collab.push(new Separator().setVariation(ESeparator.YELLOW));
    } else {
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Ship");
      collab.push(this);
    }
    return collab;
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
          results.push(container);
          break;
        case "Ship":
          let ship = new Ship(node);
          this.totalValueCalculated += ship.item.baseprice;
          results.push(ship);
          break;
        case "AssetGroup":
          let group = new AssetGroup(node);
          results.push(group);
          break;
        default:
          //        results.push(node);
          break;
      }
    }
    return results;
  }
}
