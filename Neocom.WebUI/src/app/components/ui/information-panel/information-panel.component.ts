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
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
//--- ANIMATIONS
import { trigger } from '@angular/animations';
import { state } from '@angular/animations';
import { style } from '@angular/animations';
import { transition } from '@angular/animations';
import { animate } from '@angular/animations';
import { keyframes } from '@angular/animations';
import { query } from '@angular/animations';
import { stagger } from '@angular/animations';
//--- INTERFACES
//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- COMPONENTS
import { NeoComNodeComponent } from '../../../modules/neocom-models/components/neocomnode.component';
//--- MODELS
import { InformationPanel } from '../../../uimodels/InformationPanel.model';
import { UIIconReference } from '../../../interfaces/IIconReference.interface';
import { FittingRequest } from '../../../models/FittingRequest.model';

/**
This component will allow to drop fittings onto it. ONce a fitting is added then we should wrap it into a Fitting Request so we can also add the number of copies and this information should also be pushed to the backend for persistence.
*/
@Component({
  selector: 'neocom-information-panel',
  templateUrl: './information-panel.component.html',
  styleUrls: ['./information-panel.component.scss']
})
export class InformationPanelComponent extends NeoComNodeComponent {
  @Input() node: InformationPanel;
  constructor(private appModelStore: AppModelStoreService) {
    super();
  }
  //--- EVENTS
  public onItemDrop(event: any) {
    // Generate the wrapping data.
    let identifier = this.appModelStore.myUniqueID();
    // Get the dropped data here
    this.node.addContent(this.appModelStore, new FittingRequest({ "requestId": identifier, "fitting": event.dragData }));
    this.viewer.notifyDataChanged();
  }
  //--- UI MODIFIERS
  public getIconUrl(): string {
    if (null != this.node)
      return new UIIconReference('defaulticonplaceholder').getReference()
  }
  public hasIcon(): boolean {
    return true;
  }
}
