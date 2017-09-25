import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { NeoComCharacter } from '../models/NeoComCharacter.model';
import { Pilot } from '../models/Pilot.model';
import { Corporation } from '../models/Corporation.model';
import { Separator } from '../models/Separator.model';

export class Login extends NeoComNode {
  // private downloaded: boolean = false;
  // private _pilotRoaster: NeoComCharacter[] = null;
  // private downloadPending: boolean = false;

  //  public loginid: string = "-ID-";
  public name: string = "-ID-";
  public characters: NeoComCharacter[] = null;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Login";
    // Update the list of characters to point to this login to start the chain.
    let newchars = [];
    for (let node of this.characters) {
      let newnode = null;
      switch (node.jsonClass) {
        case "Corporation":
          newnode = new Corporation(node);
          newnode.setLoginReference(this);
          newchars.push(newnode);
          break;
        case "Pilot":
          newnode = new Pilot(node);
          newnode.setLoginReference(this);
          newchars.push(newnode);
          break;
        default:
          newchars.push(node);
          break;
      }
    }
    // Replace the processed characters.
    this.characters = newchars;
  }

  /**
  Search the Character by its id and then select if as the current character for next operations.
  */
  public accessCharacterById(id: number): NeoComCharacter {
    if (null != this.characters) {
      for (let c of this.characters) {
        if (c.getId() == id) return c;
      }
    }
    return null;
    //  throw new TypeError("Character identifier " + id + " not found. Cannot select that NeoComCharacter");
  }
  public accessPilotRoaster(downloadService: AppModelStoreService): Observable<NeoComCharacter[]> {
    if (this.downloaded)
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this.characters);
        }, 100);
        setTimeout(() => {
          observer.complete();
        }, 100);
      });
    else {
      this.downloaded = true;
      return downloadService.getBackendPilotRoaster(this.getLoginId());
    }
  }
  public setPilotRoaster(list: NeoComCharacter[]): void {
    this.characters = list;
  }
  //---  G E T T E R S   A N D   S E T T E R S
  public getLoginId(): string {
    return this.name;
  }
  public getPanelIcon(): string {
    return "login.png";
  }
  /**
  Gets the number of Characters associated to this Login. This attribute has no value until the Login is selected and then the page changes. So to give it a little of functionality it should fire the download when the data is not present.
  But the task is not easy since the class has no access to any of the Services. It should have a Service sent by parameter to be able to complete the operation.
  */
  public getKeyCount(): number {
    if (this.downloaded)
      return this.characters.length;
    else return 0;
  }
  // public getKeyCountObsrver(downloadService: AppModelStoreService): number {
  //   if (this.downloaded) return this.characters.length;
  //   else {
  //     if (this.downloadPending) return 0;
  //     else {
  //       this.downloadPending = true;
  //       // Get the pilot roaster and then calculate the count.
  //       downloadService.getBackendPilotRoaster(this.getLoginId())
  //         .subscribe(result => {
  //           console.log("--[Login.getKeyCount.getBackendPilotRoaster]>Roaster: " + JSON.stringify(result));
  //           // The the list of planetary resource lists to the data returned.
  //           this._pilotRoaster = result;
  //           this.downloaded = true;
  //           this.downloadPending = false;
  //           return this._pilotRoaster.length;
  //         });
  //     }
  //   }
  // }
  /**
  Add to the content of the list to render depending on the expanded state and the contents. This do not extends the collaborarion to the grand children even that should be reviewed.
  */
  public collaborate2View(variant: EVariant): NeoComNode[] {
    let collab = [];
    // If the node is expanded then add its assets.
    if (this.expanded) {
      //  collab.push(new Separator());
      console.log("--[Login.collaborate2View]>Collaborating " + this.jsonClass);
      collab.push(this);
      // Process each item at the rootlist for more collaborations.
      for (let node of this.characters) {
        if (node.jsonClass == "Pilot") {
          let pilot = new Pilot(node)
          // let partialcollab = asset.collaborate2View(variant);
          // for (let partialnode of partialcollab) {
          console.log("--[Login.collaborate2View]>Collaborating " + pilot.jsonClass);
          collab.push(pilot);
          // }
        }
        if (node.jsonClass == "Corporation") {
          let corp = new Corporation(node)
          // let partialcollab = asset.collaborate2View(variant);
          // for (let partialnode of partialcollab) {
          console.log("--[Login.collaborate2View]>Collaborating " + corp.jsonClass);
          collab.push(corp);
          // }
        }
        if (node.jsonClass == "NeoComCharacter") {
          let character = new NeoComCharacter(node)
          // let partialcollab = asset.collaborate2View(variant);
          // for (let partialnode of partialcollab) {
          console.log("--[Login.collaborate2View]>Collaborating " + character.jsonClass);
          collab.push(character);
          //    }
        }
      }
      collab.push(new Separator());
    } else {
      console.log("--[Login.collaborate2View]>Collaborating " + this.jsonClass);
      collab.push(this);
    }
    return collab;
  }
}
