//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- SERVICES
// import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
// import { INeoComNode } from '../classes/INeoComNode.interface';
// import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { EveItem } from '../models/EveItem.model';
import { SlotLocation } from '../models/SlotLocation.model';

export class FittingItem extends NeoComNode {
  private typeId: number = -1;
  private itemDetails: EveItem = null;
  public detailedFlag: SlotLocation = null;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "FittingItem";
    // Transform the input abstract items into classes.
    if (null != this.itemDetails) this.itemDetails = new EveItem(this.itemDetails);
    if (null != this.detailedFlag) this.detailedFlag = new SlotLocation(this.detailedFlag);
  }
  // --- ICOLLABORATION INTERFACE
  // --- INEOCOMNODE INTERFACE
  public getTypeId(): number {
    return this.typeId;
  }
  // --- GETTERS & SETTERS
}
