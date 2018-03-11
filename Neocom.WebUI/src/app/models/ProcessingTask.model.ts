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
// import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { Resource } from '../models/Resource.model';
import { NeoComAsset } from '../models/NeoComAsset.model';
import { Location } from '../models/Location.model';
// import { NeoComNode } from '../models/NeoComNode.model';
// import { EveItem } from '../models/EveItem.model';
// import { FittingItem } from '../models/FittingItem.model';
// import { Separator } from '../models/Separator.model';
// import { GroupContainer } from '../models/GroupContainer.model';
// import { AssetGroupIconReference } from '../models/GroupContainer.model';

export class ProcessingTask extends NeoComNode {
  public taskType: string = "BUY";
  public quantity: number = 0;
  private referencedAsset: NeoComAsset;
  private sourceLocation: Location;
  private destination: Location;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "ProcessingTask";
    // Transform the pilot field to a class Pilot.
    this.referencedAsset = new NeoComAsset(this.referencedAsset);
    this.sourceLocation = new Location(this.sourceLocation);
    this.destination = new Location(this.destination);
    this.downloaded = true;
  }
  // --- GETTERS & SETTERS
}
