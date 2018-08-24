//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5.2.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from 'app/interfaces/EPack.enumerated';
import { ESeparator } from 'app/interfaces/EPack.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
// import { Location } from '../models/Location.model';
// import { Separator } from '../models/Separator.model';

export class Property extends NeoComNode {
  public id: number = -1;
  public propertyType: string = "LOCATIONROLE";
  public stringValue: string = "MANUFACTURE";
  public numericValue: number = 6.0006526E7;
  public ownerId: number = 92002067;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Property";
  }

  //--- G E T T E R S   &   S E T T E R S
  public getPropertyType(): string {
    return this.propertyType;
  }
  public getStringValue(): string {
    return this.stringValue;
  }
  public getNumericValue(): number {
    return this.numericValue;
  }
}
