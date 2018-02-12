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
import { Region } from '../models/Region.model';
import { Location } from '../models/Location.model';

export class Manager extends NeoComNode {
  public name: string = "-MANAGER-";
  public regions: Region[] = [];
  public locations: Location[] = [];
  public regionCount: number = 0;
  public locationCount: number = 0;

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Manager";
    // Fill additional fields after the object is parsed.
    this.regionCount = Object.keys(this.regions).length;
  }
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): INeoComNode[] {
    let collab = [];
    // Add myself to the list and then if expanded add all my data depending on the Variant.
    collab.push(this);

    if (this.expanded) {
      if (variant == EVariant.PILOTMANAGERS) {
        collab.push(this);
      }
    }
    return collab;
  }
}
