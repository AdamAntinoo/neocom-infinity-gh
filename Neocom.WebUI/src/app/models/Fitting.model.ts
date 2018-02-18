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
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { INeoComNode } from '../classes/INeoComNode.interface';
import { EVariant } from '../classes/EVariant.enumerated';
import { ESeparator } from '../classes/ESeparator.enumerated';
import { ESlotGroup } from '../models/SlotLocation.model';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { EveItem } from '../models/EveItem.model';
import { FittingItem } from '../models/FittingItem.model';
import { Separator } from '../models/Separator.model';
import { GroupContainer } from '../models/GroupContainer.model';
import { AssetGroupIconReference } from '../models/GroupContainer.model';

export class Fitting extends NeoComNode {
  private fittingId: number = -1;
  private name: string = "MI Elef LS1.0";
  private description: string = "";
  private shipHullInfo: EveItem = null;
  private items: FittingItem[] = [];

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Fitting";

    // Transform object fields.
    if (null != this.shipHullInfo) this.shipHullInfo = new EveItem(this.shipHullInfo);
    if (null != this.items) {
      // Transform all the items one by one.
      let newitems: FittingItem[] = [];
      for (let item of this.items) {
        newitems.push(new FittingItem(item));
      }
      this.items = newitems;
    }
    this.downloaded = true;
  }
  // --- INEOCOMNODE INTERFACE
  public getTypeId(): number {
    return this.shipHullInfo.getTypeId();
  }
  // --- ICOLLABORATION INTERFACE
  collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): INeoComNode[] {
    let collab: INeoComNode[] = [];
    // If the node is expanded then add its assets.
    if (this.isExpanded()) {
      console.log(">>[Fitting.collaborate2View]> Collaborating: " + "Separator.YELLOW");
      collab.push(new Separator().setVariation(ESeparator.YELLOW));
      console.log(">>[Region.collaborate2View]> Collaborating: " + "Fitting");
      collab.push(this);
      // Now collaborate the contents by slot group.
      // --- HIGH GROUP
      let groupCollab: INeoComNode[] = [];
      // let foundCounter:number=0;
      for (let item of this.items) {
        // If any item found on this group add the group.
        if (item.detailedFlag.getSlotGroup() == ESlotGroup.HIGH) groupCollab.push(item);
      }
      // If there are items on the group, all all them to collaboration
      if (groupCollab.length > 0) {
        collab.push(new GroupContainer(1, "HIGH SLOT")
          .setGroupIcon(new AssetGroupIconReference("filterIconHighSlot")));
        collab = collab.concat(groupCollab);
      }
      // --- MED GROUP
      groupCollab = [];
      // let foundCounter:number=0;
      for (let item of this.items) {
        // If any item found on this group add the group.
        if (item.detailedFlag.getSlotGroup() == ESlotGroup.MED) groupCollab.push(item);
      }
      // If there are items on the group, all all them to collaboration
      if (groupCollab.length > 0) {
        collab.push(new GroupContainer(1, "MEDIUM SLOT")
          .setGroupIcon(new AssetGroupIconReference("filterIconMediumSlot")));
        collab = collab.concat(groupCollab);
      }
      // --- LOW GROUP
      groupCollab = [];
      // let foundCounter:number=0;
      for (let item of this.items) {
        // If any item found on this group add the group.
        if (item.detailedFlag.getSlotGroup() == ESlotGroup.LOW) groupCollab.push(item);
      }
      // If there are items on the group, all all them to collaboration
      if (groupCollab.length > 0) {
        collab.push(new GroupContainer(1, "LOW SLOT")
          .setGroupIcon(new AssetGroupIconReference("filterIconLowSlot")));
        collab = collab.concat(groupCollab);
      }


      // // Process each item at the rootlist for more collaborations.
      // // Apply the processing policies before entering the processing loop. Usually does the sort.
      // let sortedContents: NeoComAsset[] = this.contents.sort((n1, n2) => {
      //   if (n1.getName() > n2.getName()) {
      //     return 1;
      //   }
      //   if (n1.getName() < n2.getName()) {
      //     return -1;
      //   }
      //   return 0;
      // });
      // for (let node of sortedContents) {
      //   let partialcollab = node.collaborate2View(appModelStore, variant);
      //   for (let partialnode of partialcollab) {
      //     collab.push(partialnode);
      //   }
      // }

      collab.push(new Separator().setVariation(ESeparator.YELLOW));
    } else collab.push(this);
    return collab;
  }
  // --- GETTERS & SETTERS
  public getShipTypeId(): number {
    if (null != this.shipHullInfo) return this.shipHullInfo.itemId;
    else return -1;
  }
  public getShipName(): string {
    if (null != this.shipHullInfo) return this.shipHullInfo.name;
    else return "-";
  }
  public getShipGroup(): string {
    if (null != this.shipHullInfo) return this.shipHullInfo.getGroupName();
    else return "-SHIP-";
  }
  public getShipGroupId(): number {
    if (null != this.shipHullInfo) return this.shipHullInfo.getGroupId();
    else return 0;
  }
  public getHullGroup(): string {
    if (null != this.shipHullInfo) return this.shipHullInfo.hullGroup;
    else return "rookie";
  }
}
