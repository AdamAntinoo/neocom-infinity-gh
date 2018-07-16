//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0
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
// import 'rxjs/add/operator/share';
//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
//--- MODELS
import { NeoComNode } from '../../models/NeoComNode.model';

export class NeoComException extends NeoComNode {
  public code: string = "-UNREGISTERED-EXCEPTION-";
  public section: string = "NEOCOM";
  public message: string = "-Exception message-";
  // public jsonClass: string = "jsonClass";

  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Exception";
  }
  //--- G E T T E R S   &   S E T T E R S
  public setMessage(message: string): NeoComException {
    this.message = message;
    return this;
  }
}
