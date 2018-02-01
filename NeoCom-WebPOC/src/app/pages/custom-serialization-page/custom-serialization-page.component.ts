// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - Page Component

/** Defines the Login page. At this page we pretend to show the list of Logins available on the NeoCom database. Inisde the Login there are the associated keys and then those keys contain the list of Characters. So in a single plage we get 3 levels on the data hierarchy.
Then the User may be able to select one and then introduce the corresponding password. He/she can also add new Logins or edit the ones available by adding Keys to them. Finally the interface may allow to create and delete Logins.
*/

import { Component, OnInit } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- MODELS
import { NeoComAsset } from '../../models/NeoComAsset.model';
import { NeoComNode } from '../../models/NeoComNode.model';

@Component({
  selector: 'app-custom-serialization-page',
  templateUrl: './custom-serialization-page.component.html',
  styleUrls: ['./custom-serialization-page.component.css']
})
export class CustomSerializationPageComponent extends PageComponent implements OnInit {
  public loginViewList: NeoComNode[] = [];
  public downloading: boolean = true;
  private totalValueCalculated:number;
  private totalVolumeCalculated:number;

  constructor(private appModelStore: AppModelStoreService) {
    super();
    this.setVariant(EVariant.LOGINLIST)
  }

  /** During the initialization we should use an special Service. Services are always
   available from application start so they are the natural place to store
   application global data. So we can use the Service to retrieve from the database
   that list or to return the already cached list if that operation was already done.
   The refreshing of the ViewPort is the equivalent on Angulat for a notifyDataChanged
   on the render Adapter. This code should be used from the initialization and also from
   all other actions that change the content list to render.
   */
  ngOnInit() {
    console.log(">>[CustomSerializationPageComponent.ngOnInit]");
    this.refreshViewPort();
    console.log("<<[CustomSerializationPageComponent.ngOnInit]");
  }
  public notifyDataSetChanged(): void {
    this.refreshViewPort();
  }

  /**
  Indicates the viewer container that the model states have changed and that a new Collaborate2View
  should be executed to generate the new view list.
  */
  public refreshViewPort(): void {
    console.log(">>[CustomSerializationPageComponent.refreshViewPort]");
    this.downloading = true;
    // Call the service to get the list of Logins.
    this.appModelStore.accessAssets4Location("Beth Ripley",92223647,60014089)
      .subscribe(result => {
        console.log("--[CustomSerializationPageComponent.ngOnInit.assets4Location]> Assetlist.length: " + result.length);
        this.loginViewList = this.processDownloadedAssets(result);
        this.downloading = false;
      });
    console.log("<<[CustomSerializationPageComponent.refreshViewPort]");
  }
  private processDownloadedAssets(assets: NeoComNode[]): NeoComAsset[] {
    let results: NeoComAsset[] = [];
    for (let node of assets) {
      switch (node.jsonClass) {
        case "NeoComAsset":
          let asset = new NeoComAsset(node);
          // this.totalValueCalculated += asset.item.baseprice * asset.quantity;
          // this.totalVolumeCalculated += asset.item.volume * asset.quantity;
          results.push(asset);
          break;
        // case "SpaceContainer":
        //   let container = new SpaceContainer(node);
        //   // this.totalValueCalculated += container.item.baseprice * container.quantity;
        //   // this.totalVolumeCalculated += container.item.volume * container.quantity;
        //   results.push(container);
        //   break;
        // case "Ship":
        //   let ship = new Ship(node);
        //   this.totalValueCalculated += ship.item.baseprice;
        //   //     this.totalVolumeCalculated += container.item.volume * container.quantity;
        //   results.push(ship);
        //   break;
      }
    }
    return results;
  }
}
