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
import { PlanetaryManager } from '../../models/PlanetaryManager.model';

@Component({
  selector: 'neocom-planetary-manager-page',
  templateUrl: './planetary-manager-page.component.html',
  styleUrls: ['./planetary-manager-page.component.css']
})

export class PlanetaryManagerPageComponent extends PageComponent implements OnInit {
  public adapterViewList: NeoComNode[] = [];
  public downloading: boolean = true;
  public pilot: NeoComCharacter;

  //  public completed: boolean = false;
  //  private selectedId;

  constructor(private appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.PLANETARYMANAGER)
  }
  /**
  Initialize the view list from the data chain that starts with the Login and ends on the Planetary Manager.
  */
  ngOnInit() {
    console.log(">>[PlanetaryManagerPageComponent.ngOnInit]");
    this.downloading = true;
    // let _characterid = null;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.accessLoginList()
          .subscribe(result => {
            console.log("--[PlanetaryManagerPageComponent.ngOnInit.accessLoginList]");
            this.appModelStore.setLoginList(result);
            this.appModelStore.activateLoginById(login)
              .subscribe(result => {
                console.log("--[PlanetaryManagerPageComponent.ngOnInit.activateLoginById]");
                // We have reached the selected Login. Search now for the character.
                let selectedLogin = result;
                this.route.params.map(p => p.id)
                  .subscribe((characterid: number) => {
                    this.pilot = selectedLogin.accessCharacterById(characterid);
                    this.pilot.accessPilotDetailed(this.appModelStore)
                      .subscribe(result => {
                        console.log("--[PlanetaryManagerPageComponent.ngOnInit.activateLoginById.accessPilotManagers]");
                        // Copnserve the current Login reference.
                        result.setLoginReference(this.pilot.getLoginReference());
                        this.pilot = result;
                        // Search for the Planetary Manager from the list of Managers. There is no way to complete it differently.
                        this.pilot.accessPlanetaryManager(this.appModelStore)
                          .subscribe(result => {
                            if (null != result) {
                              if (result.jsonClass == "PlanetaryManager") {
                                let planetary = new PlanetaryManager(result);
                                let thelist = planetary.collaborate2View(this.getVariant());
                                this.adapterViewList = thelist;
                                this.downloading = false;
                              }
                            }
                          });
                      });
                  });
              });
          });
      });
    console.log("<<[PlanetaryManagerPageComponent.ngOnInit]");
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
  public getViewer(): PlanetaryManagerPageComponent {
    return this;
  }
}
