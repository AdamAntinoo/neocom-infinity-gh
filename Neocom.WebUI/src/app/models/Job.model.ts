//  PROJECT:     A5POC (A5POC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5
//  DESCRIPTION: Proof of concept projects.
//--- CORE
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
// import { INeoComNode } from '../classes/INeoComNode.interface';
// import { EVariant } from '../classes/EVariant.enumerated';
// import { ESeparator } from '../classes/ESeparator.enumerated';
// import { ESlotGroup } from '../models/SlotLocation.model';
//--- MODELS
// import { NeoComNode } from '../models/NeoComNode.model';
// import { EveItem } from '../models/EveItem.model';
// import { FittingItem } from '../models/FittingItem.model';
// import { Separator } from '../models/Separator.model';
// import { GroupContainer } from '../models/GroupContainer.model';
// import { AssetGroupIconReference } from '../models/GroupContainer.model';

export class Job /*extends NeoComNode*/ {
  private jobId: number = -1;
  private name: string = "Job Name";
  private description: string = "Job description";
  private jsonClass: string = "node";
  private downloaded: boolean = false;

  constructor(values: Object = {}) {
    // super(values);
    Object.assign(this, values);
    this.jsonClass = "Job";
    this.downloaded = true;
  }

  // --- GETTERS & SETTERS
}
