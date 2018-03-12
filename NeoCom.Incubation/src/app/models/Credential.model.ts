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
import { EVariant } from '../interfaces/EPack.enumerated';
import { ESeparator } from '../interfaces/EPack.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
// import { Pilot } from '../models/Pilot.model';
// import { NeoComCharacter } from '../models/NeoComCharacter.model';
// import { Corporation } from '../models/Corporation.model';
// import { Separator } from '../models/Separator.model';

export class Credential extends NeoComNode {
  public accountId: number = -1;
  public accountName: string;
  public tokenType: string = "Bearer";
  public isActive: boolean = false;
  public isXML: boolean = false;
  public isESI: boolean = false;

  // public pilot: Pilot = new Pilot();

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Credential";
    this.renderWhenEmpty = true;
    // Transform the pilot field to a class Pilot.
    // if (null != this.pilot) {
    //   let newpilot = new Pilot(this.pilot);
    //   this.pilot = newpilot;
    // }
    this.downloaded = true;
  }

  // --- G E T T E R S   &   S E T T E R S
  public getAccountId(): number {
    return this.accountId;
  }
  public getAccountName(): string {
    return this.accountName;
  }
  // public getPilot(): Pilot {
  //   return this.pilot;
  // }
  // /**
  // Search the Character by its id and then select if as the current character for next operations. We have no connection to the global model service so we cannot connect the character as the deafult character. This needs another external call to do that connection.
  // */
  // public accessCharacterById(id: number): NeoComCharacter {
  //   if (null != this.characters) {
  //     for (let c of this.characters) {
  //       if (c.getId() == id) return c;
  //     }
  //   }
  //   return null;
  // }
  // public accessPilotRoaster(downloadService: AppModelStoreService): Observable<NeoComCharacter[]> {
  //   if (this.downloaded)
  //     return new Observable(observer => {
  //       setTimeout(() => {
  //         observer.next(this.characters);
  //       }, 100);
  //       setTimeout(() => {
  //         observer.complete();
  //       }, 100);
  //     });
  //   else {
  //     this.downloaded = true;
  //     return downloadService.getBackendPilotRoaster(this.getLoginId());
  //   }
  // }
  // public setPilotRoaster(list: NeoComCharacter[]): void {
  //   this.characters = list;
  // }
  //---  G E T T E R S   A N D   S E T T E R S
  // /**
  // Gets the number of Characters associated to this Login. This attribute has no value until the Login is selected and then the page changes. So to give it a little of functionality it should fire the download when the data is not present.
  // But the task is not easy since the class has no access to any of the Services. It should have a Service sent by parameter to be able to complete the operation.
  // */
  // public getKeyCount(): number {
  //   if (this.downloaded)
  //     return this.characters.length;
  //   else return 0;
  // }
  //
  // /**
  // Add to the content of the list to render depending on the expanded state and the contents. This do not extends the collaborarion to the grand children even that should be reviewed.
  // If the Login is empty we can remove it form the listing or replace it by an special separator
  // */
  // public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[] {
  //   let collab = [];
  //   // If the node is expanded then add its assets.
  //   if (this.expanded) {
  //     //  collab.push(new Separator());
  //     console.log("--[Login.collaborate2View]>Collaborating " + this.jsonClass);
  //     collab.push(new Separator().setVariation(ESeparator.RED));
  //     collab.push(this);
  //     // If the list of Characters is empty then add the Empty variation Separator
  //     if (this.characters.length > 0) {
  //       // Sort the list of Characters before processing their collaborations.
  //       let sortedCharacters = this.sortCharacters(this.characters);
  //       // Process each item at the rootlist for more collaborations.
  //       for (let node of sortedCharacters) {
  //         if (node.jsonClass == "Pilot") {
  //           let pilot = new Pilot(node)
  //           console.log("--[Login.collaborate2View]>Collaborating " + pilot.jsonClass);
  //           collab.push(pilot);
  //         }
  //         if (node.jsonClass == "Corporation") {
  //           let corp = new Corporation(node)
  //           console.log("--[Login.collaborate2View]>Collaborating " + corp.jsonClass);
  //           collab.push(corp);
  //         }
  //         if (node.jsonClass == "NeoComCharacter") {
  //           let character = new NeoComCharacter(node)
  //           console.log("--[Login.collaborate2View]>Collaborating " + character.jsonClass);
  //           collab.push(character);
  //         }
  //       }
  //     } else {
  //       collab = [];
  //     }
  //     collab.push(new Separator().setVariation(ESeparator.RED));
  //   } else {
  //     console.log("--[Login.collaborate2View]>Collaborating " + this.jsonClass);
  //     collab.push(this);
  //   }
  //   return collab;
  // }
  // private sortCharacters(nodeList: NeoComCharacter[]): NeoComCharacter[] {
  //   let sortedContents: NeoComCharacter[] = nodeList.sort((n1, n2) => {
  //     if (n1.getName() > n2.getName()) {
  //       return 1;
  //     }
  //     if (n1.getName() < n2.getName()) {
  //       return -1;
  //     }
  //     return 0;
  //   });
  //   return sortedContents;
  // }
}
