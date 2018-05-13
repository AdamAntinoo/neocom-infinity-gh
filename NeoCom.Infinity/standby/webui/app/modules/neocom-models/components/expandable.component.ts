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
// import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
//--- INTERFACES
// import { IViewer } from '../../../interfaces/IViewer.interface';
import { INeoComNode } from '../../../interfaces/INeoComNode.interface';
//--- COMPONENTS
import { NeoComNodeComponent } from '../components/neocomnode.component';

/**
This class represents the functionalities that are required for most of the nodes rendered on the interface. There are simple nodes and expandable nodes that react to the right arrow click event. Expandable nodes require access to the page generator to broadcast the data set changed events.
*/
@Component({
  selector: 'notused-expandable',
  templateUrl: './notused.html'
})
export class ExpandableComponent extends NeoComNodeComponent {
  // @Input() viewer: IViewer;
  @Input() node: INeoComNode;
  private hasMenuFlag: boolean = false;

  /**
  Toggle the expand collapse status for the underlying node. This changes the expanded attribute and also indicates other visual elements to change (like the arrow or the shade of the background).
  The second action is to generate again the view llist with a new call to the page component 'refreshViewPort'.
  */
  public clickArrow() {
    this.node.toggleExpanded();
    this.viewer.notifyDataChanged();
  }

  //--- GETTERS & SETTERS
  public hasMenu(): boolean {
    return this.hasMenuFlag;
  }
  public setMenuFlag(newflag: boolean): boolean {
    this.hasMenuFlag = newflag;
    return this.hasMenuFlag;
  }
  public isExpandable(): boolean {
    return true;
  }
}
