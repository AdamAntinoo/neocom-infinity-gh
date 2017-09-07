//--- MODELS
import { Render } from '../models/Render.model';
import { Location } from '../models/Location.model';

export class Region extends Render {
  public region: string = "Region";
  public locations: Location[] = [];

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.class = "Region";
  }
  public addLocation(newlocation: Location) {
    this.locations.push(newlocation);
  }
}
