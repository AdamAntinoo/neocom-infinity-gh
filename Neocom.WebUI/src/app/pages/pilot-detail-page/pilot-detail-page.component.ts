import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
import { PilotRoasterService } from '../../services/pilot-roaster.service';
import { AppModelStoreService } from '../../services/app-model-store.service';
import { PilotListDataSourceService } from '../../services/pilot-list-data-source.service';
import { PilotManagersDataSourceService } from '../../services/pilot-managers-data-source.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- CLASSES
//import { PilotListDataSource } from '../../classes/PilotListDataSource';
import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
import { Render } from '../../models/Render.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';

@Component({
  selector: 'neocom-pilot-detail-page',
  templateUrl: './pilot-detail-page.component.html',
  styleUrls: ['./pilot-detail-page.component.css']
})

export class PilotDetailPageComponent extends PageComponent implements OnInit {
  public adapterViewList: Render[] = [];
  public downloading: boolean = true;

  public completed: boolean = false;
  private selectedId;

  public pilot: Pilot;
  constructor(private appModelStore: AppModelStoreService, private pilotManagerService: PilotManagersDataSourceService, private route: ActivatedRoute) {
    super();
    this.setVariant(EVariant.PILOTMANAGERS)
  }

  ngOnInit() {
    console.log(">>[PilotDetailPageComponent.ngOnInit]");
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures.
        this.appModelStore.setLoginById(login);
      });
    // Extract also the Pilot Identifier.
    this.route.params.map(p => p.id)
      .subscribe((characterid: number) => {
        // Set the login at the Service to update the other data structures.
        this.appModelStore.setPilotById(characterid);
      });

    // Create our unique DS locator to get the list of Managers
    let locator = new DataSourceLocator()
      .addIdentifier(this.pilotManagerService.getServiceName())
      .addIdentifier(this.getVariantName())
      .addIdentifier(this.appModelStore.accessLogin().getLoginId())
      .addNumberIdentifier(this.appModelStore.accessCharacter().getId());
    // Check if the DS has been already registered.
    let ds = this.appModelStore.searchDataSource(locator);
    if (null == ds) {
      // Register the service as a new DataSource. Set the registration parameters to the service.
      this.pilotManagerService.setLocator(locator);
      this.pilotManagerService.setVariant(this.getVariant());
      this.appModelStore.registerDataSource(this.pilotManagerService);
    }

    // Set the AppModel datasource to this datasource.
    this.appModelStore.setActiveDataSource(this.pilotManagerService);

    // Show the spinner
    this.downloading = true;
    //    this.appModelStore.registerDataSource(ds);
    this.pilotManagerService.collaborate2View()
      .subscribe(result => {
        console.log("--[PilotDetailPageComponent.ngOnInit.collaborate2View]>ManagerList: " + JSON.stringify(result));
        // The the list of planatary resource lists to the data returned.
        //      this.adapterViewList = this.pilotListService.collaborate2View();
        this.adapterViewList = result;
        //    console.log("--[PilotRoasterPageComponent.ngOnInit.collaborate2View]> Renders: " + JSON.stringify(this.adapterViewList));
        this.downloading = false;
      });
    console.log("<<[PilotDetailPageComponent.ngOnInit]");
  }
}
