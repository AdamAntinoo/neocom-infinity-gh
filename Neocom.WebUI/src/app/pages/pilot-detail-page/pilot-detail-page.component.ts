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
//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../../classes/EVariant.enumerated';
import { IDetailedEnabledPage } from '../../classes/IDetailedEnabledPage.interface';
import { PageComponent } from '../../classes/PageComponent';
import { ESeparator } from '../../classes/ESeparator.enumerated';
// import { PageComponent } from '../../classes/PageComponent';
//--- MODELS
//import { Login } from '../../models/Login.model';
// import { DataSource } from '../../models/DataSource.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { Credential } from '../../models/Credential.model';
// import { Login } from '../../models/Login.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';
import { Manager } from '../../models/Manager.model';
import { AssetsManager } from '../../models/AssetsManager.model';
import { PlanetaryManager } from '../../models/PlanetaryManager.model';
import { Separator } from '../../models/Separator.model';
//--- COMPONENTS
import { BasePageComponent } from '../../components/core/base-page/base-page.component';


@Component({
  selector: 'neocom-pilot-detail-page',
  templateUrl: './pilot-detail-page.component.html',
  styleUrls: ['./pilot-detail-page.component.css']
})

export class PilotDetailPageComponent /*extends BasePageComponent*/ implements OnInit {
  /** This exportable property will be used by the UI to know when to draw/hide the spinner. */
  public downloading: boolean = false;
  /** Node activated by hovering over it with the mouse cursor. May be null. */
  private selectedNode: NeoComNode = null;
  // public adapterViewList: Manager[] = [];
  public headerSeparator: Separator = new Separator().setVariation(ESeparator.GREEN);
  // public downloading: boolean = true;
  // public selectedLogin: Login = null;
  public pilot: Pilot = null;

  constructor(protected appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    // super(appModelStore);
    // this.setVariant(EVariant.PILOTDETAILS)
    // this.setVariant(EVariant.PILOTMANAGERS)
  }
  /**
  Gets the pilot identification parameter from the Route. With that parameter we can go back to the list of credentials and search for that identifier. We face 3 case. The list of credentials is empty because we have restarted the application so we have to download again the list and restart the search.
  The list is not empty but down not contain the credential. We return back to the ist of credentials.
  And finally the credential if found and we start to work with the Pilot contained inside.
  */
  ngOnInit() {
    console.log(">>[PilotDetailPageComponent.ngOnInit]");
    this.downloading = true;
    // Extract the login identifier from the URL structure.
    // this.route.params.map(p => p.loginid)
    //    .subscribe((login: string) => {
    //   // Set the login at the Service to update the other data structures. Pass the login id
    //   this.appModelStore.accessLoginList()
    //     .subscribe(result => {
    //       console.log("--[PilotDetailPageComponent.ngOnInit.accessLoginList]");
    //       //    this.appModelStore.setLoginList(result);
    //       this.appModelStore.activateLoginById(login)
    //         .subscribe(result => {
    console.log("--[PilotDetailPageComponent.ngOnInit.activateLoginById]");
    // We have reached the selected Login. Search now for the character.
    // this.selectedLogin = result;
    this.appModelStore.accessCredentialList()
      .subscribe(result => {
        this.route.params.map(p => p.id)
          .subscribe((characterid: number) => {
            this.pilot = this.appModelStore.activatePilotById(Number(characterid));
            this.downloading = false;
            // Download the managers.
            // this.pilot.accessPilotManagers(this.appModelStore)
            //   .subscribe(result => {
            //     console.log("--[PilotDetailPageComponent.ngOnInit.accessPilotManagers]");
            //     this.pilot = result;
            //     // Hide the spinner.
            //     this.downloading = false;
            //     // // The the list of pilot managers should be stored at the pilot.
            //     // this.adapterViewList = this.pilot.collaborate2View(this.appModelStore, this.getVariant());
            //     // this.downloading = false;
            //   });
          });
      });
    //     });
    // });
    console.log("<<[PilotDetailPageComponent.ngOnInit]");
  }
  public getTitleData(): string {
    return "Pilot Details";
  }
  public getSubtitleData(): string {
    if (null != this.pilot) return this.pilot.getName();
    else return "";
  }
  // //--- IDATASOURCE INTERFACE
  // public applyPolicies(contents: Credential[]): Credential[] {
  //   return contents;
  // }
  // /** Set the hovered and select node to be exported. */
  // public enterSelected(target: NeoComNode) {
  //   this.selectedNode = target;
  // }
  public getViewer(): PilotDetailPageComponent {
    return this;
  }
  // --- DETAILED ENABLED INTERFACE PAGE
	/**
	Returns the current node the cursor is hovering. The hovering function is the responsible to control the item selected.
	*/
  public getSelectedNode(): NeoComNode {
    return this.selectedNode;
  }
}
