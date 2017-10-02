//--- INTERFACES
import { ESeparator } from '../classes/ESeparator.enumerated';

export class Separator {
  public jsonClass: string = "Separator";
  public variation: ESeparator = ESeparator.ORANGE;

  constructor(values: Object = {}) {
    this.jsonClass = "Separator";
  }
  public setVariation(newstate: ESeparator): Separator {
    this.variation = newstate;
    return this;
  }
}
