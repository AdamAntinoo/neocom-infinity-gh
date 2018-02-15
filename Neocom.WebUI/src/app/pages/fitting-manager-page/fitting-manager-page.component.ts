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
import { INeoComNode } from '../../classes/INeoComNode.interface';
//--- MODELS
import { PageDataSource } from '../../models/PageDataSource.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { Pilot } from '../../models/Pilot.model';
import { GroupContainer } from '../../models/GroupContainer.model';
import { Fitting } from '../../models/Fitting.model';
import { AssetGroupIconReference } from '../../models/GroupContainer.model';
import { URLGroupIconReference } from '../../models/GroupContainer.model';
// import { Credential } from '../../models/Credential.model';
// import { Fitting } from '../../models/Fitting.model';
// import { PageDataSource } from '../../models/PageDataSource.model';
//--- COMPONENTS
// import { BasePageComponent } from '../../components/core/base-page/base-page.component';

/**
Reads from the backend the bare list of Pilot fittings and with that data start to classify them into the Ship categories and then into the Ship class so all fittings will land on a 2 level hierarchy. The Fitting is also expandable to show its components classified by the slot class or the cargo holder that contains each of the items.
This is the first example for a local transformation processing into a rendering component hierarchy. Instead leaving this decissions to the backend we are going to implement the Angular equivalence to the Generator/DataSource part/list generation.
*/
@Component({
  selector: 'neocom-fitting-manager-page',
  templateUrl: './fitting-manager-page.component.html',
  styleUrls: ['./fitting-manager-page.component.css']
})
export class FittingManagerPageComponent extends PageDataSource implements OnInit, IDetailedEnabledPage {
  /** Node activated by hovering over it with the mouse cursor. May be null. */
  private selectedNode: INeoComNode = null;

  private characterid: number = -1;
  private pilot: Pilot = null;
  private groupList: Map<number, GroupContainer> = new Map<number, GroupContainer>();
  private shipList: Map<number, GroupContainer> = new Map<number, GroupContainer>();

  constructor(protected appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super(appModelStore);
  }

  ngOnInit() {
    console.log(">> [FittingManagerPageComponent.ngOnInit]");
    // Set the variant identifier for this Page. This is a Fragment property but can be generalized for pages.
    this.setVariant(EVariant.FITTINGLIST)
    // Start to show the spinner.
    this.downloading = true;
    // Call the service to be sure we have available the list of Credentials.
    this.appModelStore.accessCredentialList()
      .subscribe(result => {
        this.route.params.map(p => p.id)
          .subscribe((cid: number) => {
            this.characterid = Number(cid);
            this.pilot = this.appModelStore.activatePilotById(Number(this.characterid));
            // Download the list of fittings for tis Pilot.
            this.appModelStore.accessPilotFittings(this.characterid)
              .subscribe(result => {
                console.log(">> [FittingManagerPageComponent.ngOnInit.accessPilotFittings]>Fittings number: " + result.length);
                let fittings: Fitting[] = result;
                // Process the fittings and classify them into Ship categories, then ship type end then fitting.
                for (let fit of fittings) {
                  // Search for this ship type on the list of ships.
                  let hitShip = this.shipList.get(fit.getShipTypeId());
                  if (null == hitShip) {
                    // Create a new entry and also check the group.
                    console.log(">> [FittingManagerPageComponent]>Creating Group: " + fit.getShipName());
                    hitShip = new GroupContainer(fit.getShipTypeId(), fit.getShipName())
                      .setGroupIcon(new URLGroupIconReference(fit.getShipTypeId()));
                    this.shipList.set(fit.getShipTypeId(), hitShip);
                    let groupId = fit.getShipGroupId();
                    // Search for this group on the current list or create a new group.
                    let hitGroup = this.groupList.get(groupId);
                    if (null == hitGroup) {
                      let group = fit.getShipGroup();
                      console.log(">> [FittingManagerPageComponent]>Creating Group: " + group);
                      hitGroup = new GroupContainer(groupId, group)
                        .setGroupIcon(new AssetGroupIconReference(fit.getHullGroup()));
                      this.groupList.set(groupId, hitGroup);
                      // Add the new group to the dta content root.
                      this.dataModelRoot.push(hitGroup);
                      // Add the Ship group to the category group.
                      hitGroup.addContent(hitShip);
                    }
                  }
                  // Add the fitting to the current Ship type group as the final step.
                  hitShip.addContent(fit);
                }
              });
          });
      });

    // Hide the spinner.
    this.downloading = false;
    console.log("<< [FittingManagerPageComponent.ngOnInit]");
  }

  // --- IDATASOURCE INTERFACE
  public applyPolicies(contents: GroupContainer[]): GroupContainer[] {
    console.log("><[FittingManagerPageComponent.applyPolicies]");
    // Sort the Credentials by name.
    let sortedContents: GroupContainer[] = contents.sort((n1, n2) => {
      if (n1.getGroupTitle() < n2.getGroupTitle()) {
        return -1;
      }
      if (n1.getGroupTitle() > n2.getGroupTitle()) {
        return -1;
      }
      return 0;
    });
    return sortedContents;
  }
  /** Set the hovered and select node to be exported. */
  public enterSelected(target: INeoComNode) {
    this.selectedNode = target;
  }

  // --- DETAILED ENABLED INTERFACE PAGE
  /**
  Returns the current node the cursor is hovering. The hovering function is the responsible to control the item selected.
  */
  public getSelectedNode(): INeoComNode {
    return this.selectedNode;
  }
}
