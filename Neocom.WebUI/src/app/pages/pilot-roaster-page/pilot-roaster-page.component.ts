import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Observable } from 'rxjs/Rx';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
import { PilotListDataSourceService } from '../../services/pilot-list-data-source.service';
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
  selector: 'neocom-pilot-roaster-page',
  templateUrl: './pilot-roaster-page.component.html',
  styleUrls: ['./pilot-roaster-page.component.css']
})
export class PilotRoasterPageComponent extends PageComponent implements OnInit {
  //  public datasourceData: Pilot[] = [];
  public adapterViewList: Render[] = [];
  public downloading: boolean = true;

  constructor(private appModelStore: AppModelStoreService, private pilotListService: PilotListDataSourceService, private route: ActivatedRoute) {
    super();
    this.setVariant(EVariant.PILOTROASTER)
  }

  /**
  Components from page initialization will start the process to check the existence of the registration of the DataSource before parametrizing end registering a new one. DataSources are services so they are not instantiated by my code but by Angular itself.
  After the DS is registered we start the view generation process to feed the render looper.
  */
  ngOnInit() {
    console.log(">>[PilotRoasterPageComponent.ngOnInit]");
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.setLoginById(login);
      });

    // Create our unique DS locator.
    let locator = new DataSourceLocator()
      .addIdentifier(this.pilotListService.getServiceName())
      .addIdentifier(this.getVariantName())
      .addIdentifier(this.appModelStore.accessLogin().getLoginId());
    // Check if the DS has been already registered.
    let ds = this.appModelStore.searchDataSource(locator);
    if (null == ds) {
      // Register the service as a new DataSource. Set the registration parameters to the service.
      this.pilotListService.setLocator(locator);
      this.pilotListService.setVariant(this.getVariant());
      this.appModelStore.registerDataSource(this.pilotListService);
    }

    // Set the AppModel datasource to this datasource.
    this.appModelStore.setActiveDataSource(this.pilotListService);

    // Show the spinner
    this.downloading = true;
    //    this.appModelStore.registerDataSource(ds);
    this.pilotListService.collaborate2View()
      .subscribe(result => {
        console.log("--[PilotRoasterPageComponent.ngOnInit.collaborate2View]> pilot list: " + JSON.stringify(result));
        // The the list of planetary resource lists to the data returned.
        this.adapterViewList = result;
        this.downloading = false;
      });
    console.log("<<[PilotRoasterPageComponent.ngOnInit]");
  }
}
