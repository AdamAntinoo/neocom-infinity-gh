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
//--- MODELS
// import { NeoComNode } from '../models/NeoComNode.model';
// import { Pilot } from '../models/Pilot.model';
// import { NeoComCharacter } from '../models/NeoComCharacter.model';
// import { Corporation } from '../models/Corporation.model';
// import { Separator } from '../models/Separator.model';

export class NeoComNode implements INeoComNode {
  public jsonClass: string = "NeoComNode";
  public expanded: boolean = false;
  public downloaded: boolean = false;
  public renderWhenEmpty: boolean = true;
  public visible: boolean = true;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
  // --- ICOLLABORATION INTERFACE
  public collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): INeoComNode[] {
    let collab: INeoComNode[] = [];
    collab.push(this);
    return collab;
  }
  // --- GETTERS & SETTERS
  public isExpanded(): boolean {
    return this.expanded;
  }
  public needsDownload(): boolean {
    return !this.downloaded;
  }
  public collapse(): boolean {
    this.expanded = false;
    return this.expanded;
  }
  public expand(): boolean {
    this.expanded = true;
    return this.expanded;
  }
  public setDownloadState(newstate: boolean): boolean {
    this.downloaded = newstate;
    return this.downloaded;
  }
  public toggleExpanded() {
    this.expanded = !this.expanded;
  }
  public getContentsCount(): number {
    return -1;
  }
}
