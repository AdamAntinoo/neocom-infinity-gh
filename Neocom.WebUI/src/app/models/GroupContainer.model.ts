//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms
//               , the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code maid in typescript within the Angular
//               framework.
//--- INTERFACES
//import { PageComponent } from '../../../classes/PageComponent';
// import { EVariant } from '../../../classes/EVariant.enumerated';
// import { DataSource } from '../../../classes/DataSource';
import { INeoComNode } from '../classes/INeoComNode.interface';
import { NeoComError } from '../classes/NeoComError';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
//--- INTERFACES
import { IPage } from '../classes/IPage.interface';
import { EVariant } from '../classes/EVariant.enumerated';
import { PageComponent } from '../classes/PageComponent';

export class GroupContainer extends NeoComNode {
  // private title: string = "-TITLE-";
  private groupIcon: IGroupIconReference = new AssetGroupIconReference("rookie_64.png");
  private contents: INeoComNode[] = [];

  constructor(private id: number, private title: string) {
    super();
  }

  // --- GETTERS & SETTERS
  public getGroupTitle(): string {
    return this.title;
  }
  public setGroupIcon(reference: IGroupIconReference): GroupContainer {
    this.groupIcon = reference;
    return this;
  }

  public addContent(newcontent: INeoComNode): GroupContainer {
    this.contents.push(newcontent);
    return this;
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
    return AssetGroupIconReference.FITTING_SHIP_ASSET_LOCATION + this.iconName;
  }
}
