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
import { NeoComAsset } from './NeoComAsset.model';
import { Separator } from './Separator.model';
import { Ship } from './Ship.model';

export class SpaceContainer extends NeoComAsset {

  constructor(values: Object = {}) {
    super();
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
}
