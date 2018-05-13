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
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
// import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- INTERFACES
// import { PageComponent } from '../../../classes/PageComponent';
// import { EVariant } from '../../../classes/EVariant.enumerated';
// import { DataSource } from '../../../models/DataSource.model';
// import { NeoComError } from '../../../classes/NeoComError';
//--- MODELS
import { NeoComNode } from '../../../models/NeoComNode.model';

/**
This UI component will show a detailed version of the selected node. The renderization is just the same as the used on the Factory but changing the component to use a detailed component (that feature is still undefined). This information is shown when the user hovers over a component that has detailed information.
*/
@Component({
  selector: 'neocom-detailed-container',
  templateUrl: './detailed-container.component.html',
  styleUrls: ['./detailed-container.component.css']
})
export class DetailedContainerComponent /*implements OnInit*/ {
  // This is the input element that whan it is not null will render the correct detailed view for the node.
  @Input() selectedNode: NeoComNode;
  // constructor() { }

  // ngOnInit() {
  // }
	/** Get the type of NeoCom node class that should be used by the render discriminator to select the right model component.
	Current version is deactivated.
	*/
  public getSelectedNodeClass(): string {
    if (null != this.selectedNode) return this.selectedNode.jsonClass;
  }
  public getSelectedNode(): NeoComNode {
    return this.selectedNode;
  }
}
