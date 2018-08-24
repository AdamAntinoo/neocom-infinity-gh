//  PROJECT:     CitaMed.lib(CITM.LIB)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1 Library
//  DESCRIPTION: CitaMed. Componente libreria. Este projecto contiene gran parte del código Typescript que puede
//               ser reutilizado en otros aplicativos del mismo sistema (CitaMed) o inclusive en otros
//               desarrollos por ser parte de la plataforma MVC de despliegue de nodos extensibles y
//               interacciones con elementos seleccionables.
//--- CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Inject } from '@angular/core';
//--- ROUTER
import { Router } from '@angular/router';
//--- NOTIFICATIONS
import { NotificationsService } from 'angular2-notifications';
//--- WEBSTORAGE
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
//--- SERVICES
import { AppStoreService } from 'app/services/appstore.service';
//--- INTERFACES
import { IViewer } from 'app/interfaces/IViewer.interface';
import { INode } from 'app/interfaces/INode.interface';
import { EVariant } from 'app/interfaces/EPack.enumerated';
// //--- MODELS
// // import { DataSource } from '../../../models/DataSource.model';
// import { NeoComNode } from 'app/models/NeoComNode.model';
// import { BasePageClass } from './BasePageClass.model';
// import { Credential } from 'app/models/Credential.model';
// import { NeoComSession } from 'app/models/NeoComSession.model';

/**
This is the core code that is shared by all pages. Implements most of the code to deal with the model list data and the external request for data to be rendered on the UI.
*/
@Component({
  selector: 'notused-base-page',
  templateUrl: './notused.html',
})
export class BasePageComponent implements OnInit, IViewer {
  /** Node activated by hovering over it with the mouse cursor. May be null. */
  // protected selectedNode: NeoComNode = null;
  /** This exportable property will be used by the UI to know when to draw/hide the spinner. */
  public downloading: boolean = false;
	/** This is the single pointer to the model data that is contained on this page. This is the first element than when processed with the collaborate2View process will generate the complete list of nodes to render and received by the factory from the getBodyComponents().
	This variable is accessed directly (never been null) and it if shared with all descendans during the generation process. */
  public dataModelRoot: INode[] = [];
  /** The real time updated list of nodes to render. */
  public renderNodeList: Node[] = [];
  /** This defines the rendering variant that can be used when collaborating nodes. */
  private _variant: EVariant = EVariant.DEFAULT;

  //--- C O N S T R U C T O R
  constructor(
    @Inject(SESSION_STORAGE) protected storage: WebStorageService,
    protected router: Router,
    protected toasterService: NotificationsService,
    protected appStoreService: AppStoreService) {
  }

  //--- L I F E C Y C L E   F L O W
  ngOnInit() {
    // Check the session validity.
    this.appStoreService.checkSessionActive()
      .subscribe((session) => {
        if (null == session)
          this.router.navigate(['welcome']);
      });
  }

  //--- I V I E W E R   I N T E R F A C E
  /**
   * Return the reference to the component that knows how to locate the Page to transmit the refresh events when any user action needs to update the UI.
   * @return return the viewer implementer.
   */
  public getViewer(): IViewer {
    return this;
  }
  /**
   * Reconstructs the list of nodes to be rendered from the current DataRoot and their collaborations to the view.
   */
  public notifyDataChanged(): void {
    console.log(">>[BasePageComponent.notifyDataChanged]");
    // TODO This should be improved to change only the nodes that change (replaced, removed, added)
    let copyList = [];
    // Get the initial list by applying the policies defined at the page to the initial root node contents. Policies may be sorting or filtering actions.
    let initialList = this.applyPolicies(this.dataModelRoot);
    // Generate the contents by collaborating to the view all the nodes.
    for (let node of initialList) {
      let nodes = node.collaborate2View(this.appStoreService, this._variant);
      console.log("--[BasePageComponent.notifyDataChanged]> Collaborating " + nodes.length + " nodes.");
      // Add the collaborated nodes to the list of nodes to return.
      for (let childNode of nodes) {
        copyList.push(childNode);
      }
    }
    // Once the new list is ready replace it on the display for rendering.
    this.renderNodeList = copyList;
    console.log("<<[BasePageComponent.notifyDataChanged]");
  }
  public applyPolicies(inputList: INode[]): INode[] {
    return inputList;
  }
  // --- G E T T E R S   &   S E T T E R S
  public getVariant(): EVariant {
    return this._variant;
  }
  public setVariant(newvariant: EVariant): EVariant {
    this._variant = newvariant;
    return this._variant;
  }
}
