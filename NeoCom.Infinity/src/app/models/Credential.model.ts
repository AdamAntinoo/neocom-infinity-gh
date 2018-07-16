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
//--- SERVICES
// import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from 'app/interfaces/EPack.enumerated';
import { ESeparator } from '../interfaces/EPack.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
// import { Pilot } from '../models/Pilot.model';

/**
Typecript representation for the Credential entity.
Credentials are stored on the backend repository to allow background processing and downloading of the pilot data when that data becomes stale wihout waiting for the origianl session to reconnect and perform this operation.
Credentials are used to allow access to the database records downloaded that way that belong to the originator Pilot. Also they are used bu the ESI network interface to do the netwoeks connections and authorization token renewal at the backend side. On the client side the ahothorization data is not available and not downloaded from the backend.
*/
export class Credential extends NeoComNode {
  public accountId: number = -1;
  public accountName: string;
  public tokenType: string = "Bearer";
  public dataSource: string = "tranquility";

  // public isActive: boolean = false;
  // public isXML: boolean = false;
  // public isESI: boolean = false;
  // protected pilot: Pilot = null;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Credential";
    // this.renderWhenEmpty = true;
    // // Transform the pilot field to a class Pilot.
    // if (null != this.pilot) {
    //   let p = new Pilot(this.pilot);
    //   this.pilot = p;
    // }
    // // this.downloaded = true;
  }

  // --- G E T T E R S   &   S E T T E R S
  public getAccountId(): number {
    return this.accountId;
  }
  public getAccountName(): string {
    return this.accountName;
  }
  // public getPilot(): Pilot {
  //   // if (null == this.pilot) return new Pilot();
  //   /*else*/ return this.pilot;
  // }
  // public setPilot(pilot: Pilot): void {
  //   this.pilot = pilot;
  // }
}
