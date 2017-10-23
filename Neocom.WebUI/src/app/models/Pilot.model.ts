// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure of a EVE Pilot. May depend on other classes to complete the character information hierarchy.

//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { NeoComCharacter } from './NeoComCharacter.model';

export class Pilot extends NeoComCharacter {
  public characterID: number = -1.0;
  public active: boolean = true;
  public accountBalance: number = -1.0;
  public urlforAvatar: string = "http://image.eveonline.com/character/92223647_256.jpg";
  public lastKnownLocation: string = "- HOME -";
  public name: string = "<name>";
  //  public actions: PilotAction[] = [];
  public corporation: boolean = false;


  constructor(values: Object = {}) {
    super();
    Object.assign(this, values);
    this.jsonClass = "Pilot";
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
