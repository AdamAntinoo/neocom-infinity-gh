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
//--- ENVIRONMENT
import { environment } from '../../../environments/environment';
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
// import { Router, ActivatedRoute, ParamMap } from '@angular/router';
// import 'rxjs/add/operator/switchMap';
//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
// import { ToasterService } from 'angular5-toaster';
//--- INTERFACES
import { EVariant } from 'app/interfaces/EPack.enumerated';
// import { IDetailedEnabledPage } from '../../classes/IDetailedEnabledPage.interface';
// import { INeoComNode } from 'app/interfaces/INeoComNode.interface';
//--- COMPONENTS
import { BasePageComponent } from 'app/modules/ui/basepage.component';
//--- MODELS
import { Pilot } from 'app/models/Pilot.model';
import { NeoComSession } from 'app/models/ui/NeoComSession.model';
// import { LabeledContainer } from '../../models/LabeledContainer.model';
// import { IndustryIconReference } from '../../interfaces/IIconReference.interface';
// import { Contract } from '../../models/Contract.model';
/**
Download the Pilot information from the backend and all the related public information suitable to be used on a dashboard page that should show a general view of the Pilot characteristics. Later on this page can be implemented as a multitab page with some more other options.

Pilot information extends the previous data implementations including the Corporation data and from it the Alliance core information. It also includes data about the race and genealogy and should contain as mush details as it is found on the Character panel on the official online game.
*/
@Component({
  selector: 'neocom-pilot-dashboard-page',
  templateUrl: './pilot-dashboard-page.component.html',
  styleUrls: ['./pilot-dashboard-page.component.scss']
})
export class PilotDashboardPageComponent extends BasePageComponent implements OnInit {
  public pilot: Pilot = new Pilot(); // Reference to the Pilot PublicInformation data block.

  ngOnInit() {
    console.log(">> [PilotDashboardPageComponent.ngOnInit]");
    // Set the variant identifier for this Page. This is a Fragment property but can be generalized for pages.
    this.setVariant(EVariant.PILOTINFORMATION);
    // Check session validity. This is common code running on the BasePage.
    super.ngOnInit();
    // Start to show the spinner.
    this.downloading = true;
    // Preload the PublicInformation if needed reload only when in development mode.
    this.appStoreService.accessPilotPublicData4Id(/*this.appStoreService.getActivePilotIdentifier()*/)
      .subscribe((pilotData: Pilot) => {
        this.pilot = pilotData;
        this.toasterService.info('PILOT PUBLIC DATA.', 'Pilot ' + this.pilot.getName()
          + '. Public Data block available.');
        // Hide the spinner.
        this.downloading = false;
      });
    console.log("<< [PilotDashboardPageComponent.ngOnInit]");
  }

  //--- U I   R E N D E R I N G   M E T H O D S
  public getIconUrl(): string {
    // let id = this.pilot.getTypeId();
    let id = 1;
    return "http://image.eveonline.com/Type/" + id + "_64.png";
  }
  public getCharacterClass(): string {
    // if (this.pilot.corporation == true) return "CORPORATION";
    // else
    return "PILOT";
  }
  public debugCorporation(): string {
    return JSON.stringify(this.pilot.corporation);
  }
}
