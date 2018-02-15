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
//--- INTERFACES
import { INeoComNode } from '../../../classes/INeoComNode.interface';
//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- COMPONENTS
// import { LoginPageComponent } from '../../../pages/login-page/login-page.component';
// import { ComponentFactoryComponent } from '../../factory/component-factory/component-factory.component';
//--- MODELS
import { PageDataSource } from '../../../models/PageDataSource.model';
// import { NeoComNode } from '../../../models/NeoComNode.model';
// import { Manager } from '../../../models/Manager.model';
// import { Pilot } from '../../../models/Pilot.model';

@Component({
  selector: 'neocom-expandable-node',
  templateUrl: './expandable-node.component.html',
  styleUrls: ['./expandable-node.component.css']
})
export class ExpandableNodeComponent /*implements OnInit*/ {
  @Input() viewer: PageDataSource;
  @Input() node: INeoComNode;
  private expandable: boolean = true;
  private hasMenuFlag: boolean = false;

  constructor(private appModelStore: AppModelStoreService) { }

  /**
  Return the value of the flag that signals if this component is expandable. This would activate the right arrow on the UI part of the component.
  */
  public isExpandable(): boolean {
    return this.expandable;
  }
  /**
  Returns the value set for this component instance related to the existence of an action menu. This will activate the Menu UI part of the component.
  */
  public hasMenu(): boolean {
    return this.hasMenuFlag;
  }
  /**
  Toggle the expand collapse status. This changes the expanded attribute and also indicates other visual elements to change (like the arrow or the shade of the background).
  The second action is to generate again the view llist with a new call to the page component 'refreshViewPort'.
  */
  public clickArrow() {
    this.node.toggleExpanded();
    this.viewer.notifyDataChanged();
  }
  public getContentCount(): number {
    if (null != this.node) return this.node.getContentsCount();
  }
}
