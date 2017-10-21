// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the atributes for a common and core data hierarchy node.

import { Observable } from 'rxjs/Rx';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
import { INeoComNode } from '../classes/INeoComNode.interface';
//--- MODELS
//import { NeoComNode } from '../models/NeoComNode.model';

export class NeoComNode implements INeoComNode {
  public jsonClass = "NeoComNode";
  public expanded: boolean = false;
  public downloaded: boolean = false;
  public renderWhenEmpty: boolean = true;
  public visible: boolean = true;

  constructor(values: Object = {}) {
    Object.assign(this, values);
    this.jsonClass = "Node";
  }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
    let collab: NeoComNode[] = [];
    collab.push(this);
    return collab;
  }
  public toggleExpanded() {
    this.expanded = !this.expanded;
  }
}
