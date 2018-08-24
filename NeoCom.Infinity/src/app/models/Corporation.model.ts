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
import { Alliance } from '../models/Alliance.model';
// import { Pilot } from '../models/Pilot.model';
import { Location } from '../models/Location.model';
// import { NeoComNode } from '../models/NeoComNode.model';
// import { EveItem } from '../models/EveItem.model';
// import { FittingItem } from '../models/FittingItem.model';
// import { Separator } from '../models/Separator.model';
// import { GroupContainer } from '../models/GroupContainer.model';
// import { AssetGroupIconReference } from '../models/GroupContainer.model';

export class Corporation extends NeoComNode {
  public corporationId: number = -1.0;
  public name: string = "-NAME-";
  public ticker: string = "TICK";
  public memberCount: number = 0;
  public allianceId: number = -5;
  public url4Icon: string = "http://image.eveonline.com/Corporation/1427661573_64.png";
  public corporation: boolean = true;

  public alliance: Alliance = new Alliance();
  // public ceo: Pilot = new Pilot()
  public homeStation: Location = new Location();

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Corporation";
    // Transform the pilot field to a class Pilot.
    if (null != this.alliance) {
      let newalliance = new Alliance(this.alliance);
      this.alliance = newalliance;
    }
    // if (null != this.ceo) {
    //   let newceo = new Pilot(this.ceo);
    //   this.ceo = newceo;
    // }
    if (null != this.homeStation) {
      let newlocation = new Location(this.homeStation);
      this.homeStation = newlocation;
    }
  }

  //--- GETTERS & SETTERS
  public getId(): number {
    return this.corporationId;
  }
  public getName(): string {
    return this.name;
  }
  public getIconUrl(): string {
    return this.url4Icon;
  }
}
