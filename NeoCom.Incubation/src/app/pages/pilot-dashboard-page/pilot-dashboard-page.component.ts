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
//--- ANIMATIONS
import { trigger } from '@angular/animations';
import { state } from '@angular/animations';
import { style } from '@angular/animations';
import { transition } from '@angular/animations';
import { animate } from '@angular/animations';
import { keyframes } from '@angular/animations';
import { query } from '@angular/animations';
import { stagger } from '@angular/animations';
//--- ROUTER
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
//--- NOTIFICATIONS
import { ViewContainerRef } from '@angular/core';
// import { ToastsManager } from 'ng2-toastr/ng2-toastr';
//--- ENVIRONMENT
import { environment } from 'src/environments/environment';
//--- SERVICES
import { AppModelStoreService } from 'src/app/services/app-model-store.service';
//--- INTERFACES
import { EVariant } from 'src/app/interfaces/EVariant.enumerated';
// import { IDetailedEnabledPage } from '../../classes/IDetailedEnabledPage.interface';
import { INeoComNode } from 'src/app/interfaces/INeoComNode.interface';
//--- COMPONENTS
import { BasePageComponent } from 'src/app/pages/base-page.component';
//--- MODELS
// import { Corporation } from 'src/app/models/Corporation.model';
// import { LabeledContainer } from '../../models/LabeledContainer.model';
// import { IndustryIconReference } from '../../interfaces/IIconReference.interface';
// import { Contract } from '../../models/Contract.model';
/**
Download the Pilot public and dashboard information from the backend using the new session authorization service. We still use the same URL to convey information but just for checking the coordination between the session and the client data.
Once the data is downloaded it is stored on the application data service for later use. Pilot information is much more complete than the up to now used character information. It will connect to the Corporation detail data and from it to the Alliance base information. Also there is provisioning for race and gender.
*/
@Component({
  selector: 'neocom-pilot-dashboard-page',
  templateUrl: './pilot-dashboard-page.component.html',
  styleUrls: ['./pilot-dashboard-page.component.scss']
})
export class PilotDashboardPageComponent extends BasePageComponent implements OnInit {
  // private corporationId: number = -1;
  // public corporation: Corporation = new Corporation();

  constructor(protected appModelStore: AppModelStoreService
    // , protected toastr: ToastsManager
    , protected vcr: ViewContainerRef
    , private route: ActivatedRoute, private router: Router) {
    super(appModelStore, /*toastr,*/ vcr);
  }
  ngOnInit() {
    // Load the parameter corporation into the container. Read data from the backend to fill the models.
    console.log(">> [CorporationCardPageComponent.ngOnInit]");
    // Set the variant identifier for this Page. This is a Fragment property but can be generalized for pages.
    this.setVariant(EVariant.FITTING_ACTIONS_BYCLASS)
    // Start to show the spinner.
    this.downloading = true;
    // this.route.params.map(path => path.id)
    //   .subscribe((pilotid: number) => {
    // this.corporationId = 1427661573;
    // // Get the fitting id to process.
    // this.route.params.map(path => path.corpid)
    //   .subscribe((patcorpidid: number) => {
    //     this.corporationId = patcorpidid;
    //     // Go the backend to get the full Corporation public data.
    //     this.appModelStore.accessCorporationPublicData4Id(this.corporationId)
    //       .subscribe((corpData: Corporation) => {
    //         this.corporation = corpData;
    //         // Hide the spinner.
    //         this.downloading = false;
    //       });
    //   });
    console.log("<< [CorporationCardPageComponent.ngOnInit]");
  }
}
