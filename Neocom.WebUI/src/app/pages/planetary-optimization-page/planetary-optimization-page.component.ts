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
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';
import { Manager } from '../../models/Manager.model';
import { ProcessingAction } from '../../models/ProcessingAction.model';

@Component({
  selector: 'neocom-planetary-optimization-page',
  templateUrl: './planetary-optimization-page.component.html',
  styleUrls: ['./planetary-optimization-page.component.css']
})
export class PlanetaryOptimizationPageComponent extends PageComponent implements OnInit {
  public adapterViewList: NeoComNode[] = [];
  public downloading: boolean = true;
  public pilot: NeoComCharacter;

  constructor(private appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.PLANETARYOPTIMIZATION)
  }

  ngOnInit() {
    this.adapterViewList.push(new ProcessingAction());
  }
  public getTargetLocation(): number {
    return 60014089;
  }
  /**
  Indicates the viewer container that the model states have changed and that a new Collaborate2View should be executed to generate the new view list.
  */
  public refreshViewPort(): void {
    this.downloading = true;
    // Get the list of Managers that can be accessed for this Character.
    this.pilot.accessPlanetaryManager(this.appModelStore)
      .subscribe(result => {
        console.log("--[PilotDetailPageComponent.ngOnInit.accessPilotRoaster]>ManagerList: " + JSON.stringify(result));
        // Store the result at the PIlot.
        // let planetary = result[0];
        // this.pilot.setPlanetaryManager(planetary);
        // Call the collaboration methods to start the model view list.
        let thelist = result.collaborate2View(this.getVariant());
        this.adapterViewList = thelist;
        this.downloading = false;
      });

  }
  public getViewer(): PlanetaryOptimizationPageComponent {
    return this;
  }
}
