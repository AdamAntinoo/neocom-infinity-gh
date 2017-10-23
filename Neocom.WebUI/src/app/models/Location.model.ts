//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { Asset } from '../models/Asset.model';
import { Separator } from '../models/Separator.model';
import { Container } from '../models/Container.model';
import { Ship } from '../models/Ship.model';

export class Location extends NeoComNode {
  private totalValueCalculated: number = -1;
  private totalVolumeCalculated: number = -1;

  public stationID;
  public systemID;
  public constellationID;
  public regionID;
  public name: string = "-NAME-";
  public realId: number = -2;
  public typeID: string = "CCPLOCATION";
  public contents = [];
  public contentSize: number = 0;

  //  public stackCount: number = 0;

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
    //    this.stackCount = this.contents.length;
  }
  public getFormattedStackCount(): string {
    if (this.contentSize == 0) return "-";
    else return this.contentSize.toFixed(0).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
  }
  /**
  This is the standard method to generate the list of elements that should be rendered on the viewer page. For a Location the collaboration of a collapsed one the result should be itself. For an expanded location we should recursively add the Locations contents collaboration to the list. So a Location with a Container will check also the state of that Container.
  When the Location is expanded se add two Separators, one before the Location and another after the collaborated list of nodes.
  The list of assets collaborated should be ordered by name, but taking on mind not to include on that order their contents at the same time. So the ordering should be made before the collaboration loop.
  */
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
    let collab = [];
    // If the node is expanded then add its assets.
    if (this.expanded) {
      // Check if the contents of the Location are downloaded.
      if (this.downloaded) {
        collab.push(new Separator().setVariation(ESeparator.ORANGE));
        collab.push(this);
        // Process each item at the rootlist for more collaborations.
        // Apply the processing policies before entering the processing loop. Usually does the sort.
        let sortedContents: Asset[] = this.contents.sort((n1, n2) => {
          if (n1.getName() > n2.getName()) {
            return 1;
          }
          if (n1.getName() < n2.getName()) {
            return -1;
          }
          return 0;
        });
        for (let node of sortedContents) {
          switch (node.jsonClass) {
            case "NeoComAsset":
              let asset = new Asset(node);
              let partialcollab = asset.collaborate2View(appModelStore, variant);
              for (let partialnode of partialcollab) {
                collab.push(partialnode);
              }
              break;
            case "SpaceContainer":
              let container = new Container(node);
              let containerCollaboration = container.collaborate2View(appModelStore, variant);
              for (let partialnode of containerCollaboration) {
                collab.push(partialnode);
              }
              break;
          }
        }
      } else {
        // Call the backend to download the contents. On callback we need to fire an event to refresh the display.
        appModelStore.getBackendLocationsContents(this.getLocationId())
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
      collab.push(new Separator().setVariation(ESeparator.ORANGE));
    } else collab.push(this);
    return collab;
  }
  public getName(): string {
    return this.name;
  }
  /**
  This method should know the internal representation of the different types of Locations and return the unique ID that represents the unique locator. If the Location is a CCP location this is a selector depending on the available values on the system, constellation or region.
  */
  public getLocationId(): number {
    if (this.typeID == "CCPLOCATION")
      return Math.max(Math.max(Math.max(this.stationID, this.systemID), this.constellationID), this.regionID);
    if (this.typeID == "UNKNOWN")
      return this.stationID;
    return this.realId;
  }
  private processDownloadedAssets(assets: NeoComNode[]): NeoComNode[] {
    let results: NeoComNode[] = [];
    for (let node of assets) {
      switch (node.jsonClass) {
        case "NeoComAsset":
          let asset = new Asset(node);
          this.totalValueCalculated += asset.item.baseprice * asset.quantity;
          this.totalVolumeCalculated += asset.item.volume * asset.quantity;
          results.push(asset);
          break;
        case "SpaceContainer":
          let container = new Container(node);
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
}
