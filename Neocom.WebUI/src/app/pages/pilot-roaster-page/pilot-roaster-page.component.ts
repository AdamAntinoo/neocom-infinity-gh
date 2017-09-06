import { Component, OnInit } from '@angular/core';
//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../../interfaces/EVariant.enumerated';
//--- CLASSES
import { PilotListDataSource } from '../../classes/PilotListDataSource';
import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
import { Pilot } from '../../models/Pilot';

@Component({
  selector: 'neocom-pilot-roaster-page',
  templateUrl: './pilot-roaster-page.component.html',
  styleUrls: ['./pilot-roaster-page.component.css']
})
export class PilotRoasterPageComponent implements OnInit {
  public datasourceData: Pilot[] = [];

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
    console.log(">>[PilotRoasterPageComponent.ngOnInit]");
    // Create the DataSource and initialize it with the parameters.
    let locator = new DataSourceLocator().addIdentifier(this.getVariant());
    let ds = new PilotListDataSource(locator);
    ds = this.appModelStore.registerDataSource(ds);
    ds.setVariant(this.getVariant());
    //    ds.setCacheable(true);
  }
  /**
  Calls the same method of the associated DataSource to get the list of nodes that currently are collaborating
  to the view presentation.
  */
  public collaborate2View() {
    let r = new Pilot();
    this.datasourceData.push(r);

  }
  private getVariant(): string {
    return EVariant[EVariant.PILOTROASTER];
  }
}
