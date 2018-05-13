//--- INTERFACES
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';

export class Separator extends NeoComNode {
  public jsonClass: string = "Separator";
  public variation: ESeparator = ESeparator.ORANGE;

  constructor(values: Object = {}) {
    super(values);
    //  this.jsonClass = "Separator";
  }
  public setVariation(newstate: ESeparator): Separator {
    this.variation = newstate;
    return this;
  }
}
