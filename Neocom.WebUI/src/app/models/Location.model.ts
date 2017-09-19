//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';

export class Location extends NeoComNode {
  public location: string = "LOCATION";
  public position: string;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Location";
  }
}
