// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - CLASS
// DESCRIPTION: Defines the atributes for a common and core data hierarchy node.

export class NeoComNode {
  public expanded: boolean = false;
  public downloaded: boolean = true;
  public renderWhenEmpty: boolean = true;
  public visible: boolean = true;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
