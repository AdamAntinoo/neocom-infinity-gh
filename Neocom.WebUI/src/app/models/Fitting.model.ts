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
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { INeoComNode } from '../classes/INeoComNode.interface';
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { EveItem } from '../models/EveItem.model';
import { FittingItem } from '../models/FittingItem.model';

export class Fitting extends NeoComNode {
  private fittingId: number = -1;
  private name: string = "MI Elef LS1.0";
  private description: string = "";
  private shipHullInfo: EveItem = null;
  private items: FittingItem[] = [];

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Fitting";

    // Transform object fields.
    if (null != this.shipHullInfo) this.shipHullInfo = new EveItem(this.shipHullInfo);
    if (null != this.items) {
      // Transform all the items one by one.
      let newitems: FittingItem[] = [];
      for (let item of this.items) {
        newitems.push(new FittingItem(item));
      }
      this.items = newitems;
    }
    this.downloaded = true;
  }
  // --- ICOLLABORATION INTERFACE
  // --- GETTERS & SETTERS
}
