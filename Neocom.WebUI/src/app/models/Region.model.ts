//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { Location } from '../models/Location.model';

export class Region extends NeoComNode {
  public region: string = "Region";
  public locations: Location[] = [];

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Region";
  }
  public addLocation(newlocation: Location) {
    this.locations.push(newlocation);
  }
}
