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
// import { NeoComCharacter } from '../models/NeoComCharacter.model';
// import { Pilot } from '../models/Pilot.model';
// import { Corporation } from '../models/Corporation.model';
// import { Separator } from '../models/Separator.model';

export class NeoComAsset extends NeoComNode {
  public name: string = "-ID-";
//  public characters: NeoComCharacter[] = null;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "NeoComAsset";
    this.renderWhenEmpty = false;
    // Replace the processed characters.
//    this.characters = newchars;
    this.downloaded = true;
  }

  //---  G E T T E R S   A N D   S E T T E R S
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
}
