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
//--- SERVICES
// import { AppModelStoreService } from '../../../../../services/app-model-store.service';
//--- COMPONENTS
import { NeoComNodeComponent } from '../../components/neocomnode.component';
import { ExpandableComponent } from '../../components/expandable.component';
//--- INTERFACES
//--- MODELS
import { Action } from '../../../../models/Action.model';

@Component({
  selector: 'app-action',
  templateUrl: './action.component.html',
  styleUrls: ['./action.component.scss'],
})

export class ActionComponent extends ExpandableComponent {
  @Input() node: Action;

  public getIconUrl(): string {
    return "http://image.eveonline.com/Type/" + this.getTypeId() + "_64.png";
  }
  // public isExpandable(): boolean {
  //   return true;
  // }
  // public hasMenu(): boolean {
  //   return false;
  // }
  //--- GETTERS & SETTERS
  public getTypeId(): number {
    let action = this.node as Action;
    return action.typeId;
  }
}
