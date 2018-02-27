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

export class EveItem extends NeoComNode {
  public itemId: 652;
  public name: string = "Mammoth";
  private category: string = "Ship";
  private baseprice: number = 0;
  private price: number = 0;
  private defaultprice: number = -1;
  private volume: number = 0;
  private quantity: number = 1;
  private tech: string = "Tech I";
  private industryGroup: string = "HULL";
  private groupName: string = "Industrial";
  private groupId: number = 28;
  private categoryId: number = 6;
  private blueprint: boolean = false;
  private itemID: number = 652;
  private categoryName: string = "Ship";
  public hullGroup: string = "rookie";

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "EveItem";
  }
  // --- ICOLLABORATION INTERFACE
  // --- GETTERS & SETTERS
  public getQuantity(): number {
    if (null == this.quantity) return 1;
    else return this.quantity;
  }
  public getPrice(): number {
    if (null == this.price) return 0.0;
    else return this.price;
  }
  public getVolume(): number {
    if (null == this.volume) return 0.01;
    else return this.volume;
  }
  public getTypeId(): number {
    return this.itemId;
  }
  public getGroupId(): number {
    return this.groupId;
  }
  public getGroupName(): string {
    return this.groupName;
  }
}
