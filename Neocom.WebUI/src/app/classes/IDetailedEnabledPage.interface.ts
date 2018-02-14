//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- INTERFACES
import { INeoComNode } from '../classes/INeoComNode.interface';

/**
Defined the structure of methods that should be available when a page is able to export hovering detailed information to the Detailed Information Panel.
*/
export interface IDetailedEnabledPage {
  /**
  Returns the last node hovered or null if there is not such node of the ability to export for the nodes themselves is not active. A null returned value should work as if do not apply while the existence of a node will render its component mapped by the neocom-detailed-container.
  */
  getSelectedNode(): INeoComNode;
}
