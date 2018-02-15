//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms
//               , the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code maid in typescript within the Angular
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
import { NeoComError } from '../classes/NeoComError';
import { ESeparator } from '../classes/ESeparator.enumerated';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
import { Separator } from './Separator.model';


export class GroupContainer extends NeoComNode {
  private groupIcon: IGroupIconReference = new AssetGroupIconReference("rookie_64.png");
  private contents: INeoComNode[] = [];

  constructor(private id: number, private title: string) {
    super();
    this.jsonClass = "GroupContainer";
  }

  // --- GETTERS & SETTERS
  public getGroupTitle(): string {
    return this.title;
  }
  public getGroupIconReference(): string {
    return this.groupIcon.getReference();
  }
  public setGroupIcon(reference: IGroupIconReference): GroupContainer {
    this.groupIcon = reference;
    return this;
  }

  public addContent(newcontent: INeoComNode): GroupContainer {
    this.contents.push(newcontent);
    return this;
  }
  // --- INEOCOMNODE INTERFACE
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): INeoComNode[] {
    console.log(">>[GroupContainer.collaborate2View]");
    // Initialize the list to be output.
    let collab: INeoComNode[] = [];
    // Check if the Region is expanded or not.
    if (this.isExpanded()) {
      console.log(">>[GroupContainer.collaborate2View]>Collaborating: " + "Separator.RED");
      collab.push(new Separator().setVariation(ESeparator.RED));
      console.log(">>[GroupContainer.collaborate2View]>Collaborating: " + "GroupContainer");
      collab.push(this);
      // Process each Location for new collaborations.
      for (let node of this.contents) {
        let partialcollab = node.collaborate2View(appModelStore, variant);
        for (let partialnode of partialcollab) {
          collab.push(partialnode);
        }
      }
      console.log(">>[GroupContainer.collaborate2View]>Collaborating: " + "Separator.RED");
      collab.push(new Separator().setVariation(ESeparator.RED));
    } else {
      console.log(">>[GroupContainer.collaborate2View]>Collaborating: " + "GroupContainer");
      collab.push(this);
    }
    return collab;
  }
  public getContentsCount(): number {
    return this.contents.length;
  }
}
export interface IGroupIconReference {
  getReference(): string;
}
export class URLGroupIconReference implements IGroupIconReference {
  private static FITTING_SHIP_URL_BASE = "http://image.eveonline.com/Type/";

  constructor(private iconType: number) { }
  public getReference(): string {
    return URLGroupIconReference.FITTING_SHIP_URL_BASE + this.iconType + "_64.png";
  }
}
export class AssetGroupIconReference implements IGroupIconReference {
  private static FITTING_SHIP_ASSET_LOCATION: string = "/assets/res-fitting/drawable/";

  constructor(private iconName: string) { }
  public getReference(): string {
    return AssetGroupIconReference.FITTING_SHIP_ASSET_LOCATION + this.iconName.toLowerCase() + "_64.png";
  }
}
