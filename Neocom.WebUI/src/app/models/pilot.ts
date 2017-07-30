// PROJECT:     POC-ASB-Planetary (POC.ASBP)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the structure for a list of resources. Basically the id and the name of the list and possible it can be extended to contain the list of associated resources.
export class Pilot {
  public id: number = 0.0;
  public name: string = "-Default-";

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

  public getId() {
    return this.id;
  }
  public getName() {
    return this.name;
  }
  public setName(newname: string) {
    this.name = newname;
  }
}
