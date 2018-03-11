//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
//--- INTERFACES
import { IViewer } from '../../../interfaces/IViewer.interface';
import { INeoComNode } from '../../../interfaces/INeoComNode.interface';
//--- MODELS
import { NeoComNode } from '../../../models/NeoComNode.model';

/**
This class represents the requirements requestd by the simplest node to be rendered on node containers.
*/
@Component({
  selector: 'notused-expandable',
  templateUrl: './notused.html'
})
export class NeoComNodeComponent {
  @Input() viewer: IViewer;
  // @Input() node: NeoComNode;

  //--- GETTERS & SETTERS
  public isExpandable(): boolean {
    return false;
  }
}
