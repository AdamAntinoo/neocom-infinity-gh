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
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { NeoComCharacter } from './NeoComCharacter.model';
import { Location } from './Location.model';

export class Pilot extends NeoComCharacter {
  public characterID: number = -1.0;
  public active: boolean = true;
  public accountBalance: number = -1.0;
  public urlforAvatar: string = "http://image.eveonline.com/character/92223647_256.jpg";
  public lastKnownLocation: Location = null;
  public name: string = "<name>";
  //  public actions: PilotAction[] = [];
  public corporation: boolean = false;


  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Pilot";
    // Transfor
    this.lastKnownLocation = new Location(this.lastKnownLocation);
  }

  public getId() {
    return this.characterID;
  }
  public getName() {
    return this.name;
  }
  public setName(newname: string) {
    this.name = newname;
  }
  public getUrlforAvatar() {
    return this.urlforAvatar;
  }
}
