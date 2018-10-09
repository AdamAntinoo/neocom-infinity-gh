//  PROJECT:     CitaMed.frontend(CITM.A6F)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.
//  DESCRIPTION: CitaMed. Sistema S2. Aplicación Angular adaptada para que los médicos puedan cargar sus
//               planillas de huecos de citas en el repositorio indicado de forma que los pacientes puedan
//               reservarlos. Tiene como backend el S1 de CitaMed.
//--- CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { Inject } from '@angular/core';
//--- ENVIRONMENT
// import { environment } from 'app/../environments/environment';
//--- WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
//--- ROUTER
import { Router } from '@angular/router';
//--- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- SERVICES
// import { AppModelStoreService } from 'app/services/app-model-store.service';
//--- CALENDAR
// import { startOfDay } from 'date-fns';
//--- INTERFACES
// import { EVariant, IContainerController } from 'citamed-lib';
// import { IViewer } from 'citamed-lib';
// import { INode } from 'citamed-lib';
// //--- COMPONENTS
// import { MVCViewer } from '@UIModule';
//--- MODELS
// import { DailyAppointmentsTemplate } from 'app/models/DailyAppointmentsTemplate.model';
// import { AppStoreService } from '@app/services/appstore.service';
import { IContainerController } from '../interfaces/core/IContainerController.interface';
import { INode } from '../interfaces/core/INode.interface';
import { AppStoreService } from 'projects/NeoCom-Login/src/app/services/app-store.service';
// import { Cita } from 'citamed-lib';

@Component({
  selector: 'notused-mvcontroller',
  templateUrl: './notused.html'
})
export class MVControllerComponent implements IContainerController {
  /** Node activated by hovering over it with the mouse cursor. May be null. */
  protected selectedNode: INode = null;
  /** This exportable property will be used by the UI to know when to draw/hide the spinner. */
  public downloading: boolean = false;
	/** This is the single pointer to the model data that is contained on this page. This is the first element than when processed with the collaborate2View process will generate the complete list of nodes to render and received by the factory from the getBodyComponents().
	This variable is accessed directly (never been null) and it if shared with all descendans during the generation process. */
  protected dataModelRoot: INode[] = [];
  /** The real time updated list of nodes to render. */
  public renderNodeList: INode[] = [];
  /** This defines the rendering variant that can be used when collaborating nodes. */
  private _variant: string = "-DEFAULT-";

  //--- C O N S T R U C T O R
  constructor(
    @Inject(LOCAL_STORAGE) protected storage: WebStorageService,
    protected router: Router,
    protected toasterService: NotificationsService,
    protected appStoreService: AppStoreService) {
  }

  // --- G E T T E R S   &   S E T T E R S
  public getVariant(): string {
    return this._variant;
  }
  // public getVariantName(): string {
  //   return EVariant[this._variant];
  // }
  public setVariant(variant: string): void {
    this._variant = variant;
  }

  //--- I V I E W E R   I N T E R F A C E
  /**
	Return the reference to the component that knows how to locate the Page to transmit the refresh events when any user action needs to update the UI.
	*/
  public getViewer(): IContainerController {
    return this;
  }
  public getContainer(): IContainerController {
    return this;
  }
  /**
    Reconstructs the list of nodes to be rendered from the current DataRoot and their collaborations to the view.
    */
  public notifyDataChanged(): void {
    console.log(">>[BasePageComponent.notifyDataChanged]");
    // Clear the current list while reprocessing the new nodes.
    // TODO This should be improved to change only the nodes that change (replaced, removed, added)
    // this.renderNodeList = [];
    let copyList = [];
    // Get the initial list by applying the policies defined at the page to the initial root node contents. Policies may be sorting or filtering actions.
    let initialList = this.applyPolicies(this.dataModelRoot);
    // Generate the contents by collaborating to the view all the nodes.
    for (let node of initialList) {
      let nodes = node.collaborate2View(this.getVariant());
      console.log("--[BasePageComponent.notifyDataChanged]> Collaborating " + nodes.length + " nodes.");
      // Add the collaborated nodes to the list of nodes to return.
      for (let childNode of nodes) {
        copyList.push(childNode);
      }
    }
    this.renderNodeList = copyList;
    console.log("<<[BasePageComponent.notifyDataChanged]");
  }
  public applyPolicies(_entries: INode[]): INode[] {
    return _entries;
  }
}
