//--- MODELS
import { Render } from '../models/Render.model';

export class Location extends Render {
  public location: string = "LOCATION";
  public position: string;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.class = "Location";
  }

}
