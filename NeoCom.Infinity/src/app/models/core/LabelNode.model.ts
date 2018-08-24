//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- MODELS
import { INode } from 'app/interfaces/INode.interface';
import { NeoComNode } from 'app/models/NeoComNode.model';

export class LabelNode extends NeoComNode {
  // public jsonClass: string = "LabelNode";
  public title: string = "-TITLE-";

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "LabelNode";
  }
}
