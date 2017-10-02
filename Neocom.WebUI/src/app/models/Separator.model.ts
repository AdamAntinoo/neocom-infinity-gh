//--- INTERFACES
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from '../models/NeoComNode.model';

export class Separator extends NeoComNode {
  public jsonClass: string = "Separator";
  public variation: ESeparator = ESeparator.ORANGE;

  constructor(values: Object = {}) {
    super();
    this.jsonClass = "Separator";
  }
  public setVariation(newstate: ESeparator): Separator {
    this.variation = newstate;
    return this;
  }
}
