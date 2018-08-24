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
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { ESeparator } from 'app/interfaces/EPack.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
// import { Resource } from '../models/Resource.model';
// import { NeoComAsset } from '../models/NeoComAsset.model';
// import { Location } from '../models/Location.model';
// import { NeoComNode } from '../models/NeoComNode.model';
// import { EveItem } from '../models/EveItem.model';
// import { FittingItem } from '../models/FittingItem.model';
// import { Separator } from '../models/Separator.model';
// import { GroupContainer } from '../models/GroupContainer.model';
// import { AssetGroupIconReference } from '../models/GroupContainer.model';

export class Alliance extends NeoComNode {
  public allianceId: number = -5;
  public name: string = "-NAME-";
  public ticker: string = "TICK";
  public url4Icon: string = "http://image.eveonline.com/Alliance/117383987_64.png";

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Alliance";
  }

  //--- GETTERS & SETTERS
  public getId(): number {
    return this.allianceId;
  }
  public getName(): string {
    return this.name;
  }
  public getIconUrl(): string {
    return this.url4Icon;
  }
}
