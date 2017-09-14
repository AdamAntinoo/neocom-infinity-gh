// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the atributes for a common and core data hierarchy node.
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { Render } from '../models/Render.model';

export class NeoComNode extends Render {
  public expanded: boolean = false;
  public downloaded: boolean = true;
  public renderWhenEmpty: boolean = true;
  public visible: boolean = true;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Node";
  }
  public collaborate2View(variant: EVariant): NeoComNode[] {
    let collab = [];
    collab.push(this);
    return collab;
  }
}
