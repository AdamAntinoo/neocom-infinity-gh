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
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
//--- SERVICES
import { AppModelStoreService } from 'app/services/app-model-store.service';
//--- INTERFACES
// import { EVariant } from '../interfaces/EPack.enumerated';
// import { ESeparator } from '../interfaces/EPack.enumerated';
//--- MODELS
import { NeoComNode } from 'app/models/NeoComNode.model';
import { Credential } from 'app/models/Credential.model';
// import { Pilot } from '../models/Pilot.model';

export class NeoComSession extends NeoComNode {
  public sessionId: string = "-UNIQUE-IDENTIFIER-";
  public publicKey: string = "-RSA-GENERATED-KEY-";
  public pilotIdentifier: number = -1;
  public credential: Credential = new Credential();

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "NeoComSession";
    // this.renderWhenEmpty = true;
    // // Transform the Credential field to a class Pilot.
    // if (null != this.credential) {
    //   let cred = new Credential(this.credential);
    //   this.credential = cred;
    //   this.pilotIdentifier = this.credential.getAccountId();
    // }
    // this.downloaded = true;
  }

  // --- G E T T E R S   &   S E T T E R S
  public getCredential(): Credential {
    return this.credential;
  }
  public getPilotIdentifier(): number {
    if (null != this.credential)
      return this.credential.getAccountId();
    else return this.pilotIdentifier;
  }
  // public getPilot(): Pilot {
  //   return this.credential.getPilot();
  // }
  // public storePilot(pilot: Pilot): void {
  //   if (null != this.credential) {
  //     this.credential.setPilot(pilot);
  //   }
  // }
}
