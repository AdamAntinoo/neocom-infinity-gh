//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
// import { Observable } from 'rxjs/Rx';
// // Import RxJs required methods
// import 'rxjs/add/operator/map';
// import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppStoreService } from 'app/services/appstore.service';
//--- INTERFACES
import { INode } from 'app/interfaces/INode.interface';
import { INeoComNode } from 'app/interfaces/INeoComNode.interface';
import { EVariant } from 'app/interfaces/EPack.enumerated';
import { ESeparator } from 'app/interfaces/EPack.enumerated';
//--- MODELS
import { ColorTheme } from 'app/models/ui/ColorTheme.model';

export abstract class NeoComNode implements INeoComNode {
  public jsonClass: string = "NeoComNode";
  public selected: boolean = false;
  //--- Visual properties and states.
  // public expanded: boolean = false;
  // public downloaded: boolean = true;
  // public renderWhenEmpty: boolean = true;
  // public visible: boolean = true;
  public themeColor: ColorTheme = new ColorTheme().setThemeColor(ESeparator.WHITE);

  //--- C O N S T R U C T O R
  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
  // --- I C O L L A B O R A T I O N   I N T E R F A C E
  public collaborate2View(appStoreService: AppStoreService, variant: EVariant): INode[] {
    let collab: INode[] = [];
    collab.push(this);
    return collab;
  }

  // public toggleSelected(): boolean {
  //   this.selected = !this.selected;
  //   return this.selected;
  // }
  //
  // // --- INEOCOMNODE INTERFACE
  // public /*abstract*/ getTypeId(): number { return 0; }

  // --- GETTERS & SETTERS
  // public isExpanded(): boolean {
  //   return this.expanded;
  // }
  // public needsDownload(): boolean {
  //   return !this.downloaded;
  // }
  // public collapse(): boolean {
  //   this.expanded = false;
  //   return this.expanded;
  // }
  // public expand(): boolean {
  //   this.expanded = true;
  //   return this.expanded;
  // }
  // public setDownloadState(newstate: boolean): boolean {
  //   this.downloaded = newstate;
  //   return this.downloaded;
  // }
  // public toggleExpanded() {
  //   this.expanded = !this.expanded;
  // }
  // public getContentsCount(): number {
  //   return -1;
  // }
}
