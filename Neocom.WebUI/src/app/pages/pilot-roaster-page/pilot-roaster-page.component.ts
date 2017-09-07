import { Component, OnInit } from '@angular/core';
//--- SERVICES
import { AppModelStoreService } from 'src/app//services/app-model-store.service';
import { PilotListDataSourceService } from 'src/app/services/pilot-list-data-source.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- CLASSES
import { PilotListDataSource } from '../../classes/PilotListDataSource';
import { DataSourceLocator } from 'src/app/classes/DataSourceLocator';
//--- MODELS
import { Render } from 'src/app/models/Render.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { Pilot } from '../../models/Pilot';

@Component({
  selector: 'neocom-pilot-roaster-page',
  templateUrl: './pilot-roaster-page.component.html',
  styleUrls: ['./pilot-roaster-page.component.css']
})
export class PilotRoasterPageComponent extends PageComponent implements OnInit {
  //  public datasourceData: Pilot[] = [];
  public adapterViewList: Render[] = [];
  public downloading: boolean = true;

  constructor(private appModelStore: AppModelStoreService, private pilotListService: PilotListDataSourceService) {
    super();
    this.setVariant(EVariant.PILOTROASTER)
  }

  ngOnInit() {
    console.log(">>[PilotRoasterPageComponent.ngOnInit]");
    // Create the DataSource and initialize it with the parameters.
    let locator = new DataSourceLocator()
      .addIdentifier("PilotListDataSource")
      .addIdentifier(this.getVariantName());
    let ds = new PilotListDataSource(locator);
    ds.setVariant(this.getVariant());
    // Show the spinner
    this.downloading = true;
    this.appModelStore.registerDataSource(ds);

    this._cookieService.put("login-id", "default")
    this.pilotRoasterService.getAllPilots()
      .subscribe(result => {
        console.log("--[PagePilotsComponent.ngOnInit.subscribe]> pilot list: " + JSON.stringify(result));
        // The the list of planatary resource lists to the data returned.
        this.pilotList = result;
      });



      .then(result => {
      console.log("--[PilotRoasterPageComponent.ngOnInit.registerDataSource]");
      // The the list of planatary resource lists to the data returned.
      this.adapterViewList = this.appModelStore.locateDataSource(locator).collaborate2View();
      this.downloading = false;
    });

    // Set the AppModel datasource to this datasource.
    this.appModelStore.setActiveDataSource(this.appModelStore.locateDataSource(locator));
    console.log("<<[PilotRoasterPageComponent.ngOnInit]");
  }
  /**
  Calls the same method of the associated DataSource to get the list of nodes that currently are collaborating
  to the view presentation.
  */
  public collaborate2View() {
    let ds = this.appModelStore.accessDataSource();
    if (ds != null) {
      let viewList = ds.collaborate2View();
      return viewList;
    }
  }
}
