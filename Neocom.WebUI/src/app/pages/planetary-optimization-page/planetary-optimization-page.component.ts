import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- CLASSES
import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
import { Render } from '../../models/Render.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';
import { Manager } from '../../models/Manager.model';
import { AssetsManager } from '../../models/AssetsManager.model';
import { PlanetaryManager } from '../../models/PlanetaryManager.model';
import { ProcessingAction } from '../../models/ProcessingAction.model';
import { Location } from '../../models/Location.model';

@Component({
  selector: 'neocom-planetary-optimization-page',
  templateUrl: './planetary-optimization-page.component.html',
  styleUrls: ['./planetary-optimization-page.component.css']
})
export class PlanetaryOptimizationPageComponent extends PageComponent implements OnInit {
  public adapterViewList: ProcessingAction[] = [];
  public downloading: boolean = true;
  public pilot: NeoComCharacter = null;
  public planetaryManager: PlanetaryManager = null;
  private exceptionList: Error[] = [];
  public targetLocation: Location = null;

  constructor(private appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.PLANETARYOPTIMIZATION)
  }

  /**
  Initialize the view list from the data chain that starts with the Login and ends on the Planetary Manager.
  */
  ngOnInit() {
    console.log(">>[PlanetaryOptimizationPageComponent.ngOnInit]");
    this.downloading = true;
    // let _characterid = null;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.accessLoginList()
          .subscribe(result => {
            console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.accessLoginList]");
            this.appModelStore.setLoginList(result);
            this.appModelStore.activateLoginById(login)
              .subscribe(result => {
                console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.activateLoginById]");
                // We have reached the selected Login. Search now for the character.
                let selectedLogin = result;
                this.route.params.map(p => p.id)
                  .subscribe((characterid: number) => {
                    //    this.pilot = selectedLogin.accessCharacterById(characterid);
                    this.pilot = this.appModelStore.setPilotById(characterid);
                    this.pilot.accessPilotDetailed(this.appModelStore)
                      .subscribe(result => {
                        console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.accessPilotDetailed]");
                        // Copnserve the current Login reference.
                        result.setLoginReference(this.pilot.getLoginReference());
                        this.pilot = result;
                        // Download the managers.
                        this.pilot.accessPilotManagers(this.appModelStore)
                          .subscribe(result => {
                            console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.accessPilotManagers]");
                            // The the list of pilot managers that should be stored at the pilot.
                            let man = null;
                            for (let manager of result) {
                              switch (manager.jsonClass) {
                                case "AssetsManager":
                                  man = new AssetsManager(manager);
                                  this.pilot.setAssetsManager(man);
                                  break;
                                case "PlanetaryManager":
                                  man = new PlanetaryManager(manager);
                                  this.pilot.setPlanetaryManager(man);
                                  break;
                              }
                            }
                            this.pilot.accessPlanetaryManager(this.appModelStore)
                              .subscribe(result => {
                                console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.accessPilotManagers]> ManagerList: ");
                                if (null != result) {
                                  if (result.jsonClass == "PlanetaryManager") {
                                    let planetary = new PlanetaryManager(result);
                                    this.planetaryManager = planetary;
                                    // Store back this at the pilot if we have received a new download.
                                    this.pilot.setPlanetaryManager(planetary);
                                    this.route.params.map(p => p.locationid)
                                      .subscribe((locationid: number) => {
                                        // Set the location accesible to the viewer to display its information
                                        this.targetLocation = planetary.search4Location(locationid);
                                        this.planetaryManager.getOptimizedScenario(locationid, this.appModelStore)
                                          .subscribe(result => {
                                            // We should get a list of the optimized actions. Use that list on the viewer.
                                            this.adapterViewList = result;
                                            this.downloading = false;
                                          });
                                      });
                                  }
                                }
                              });
                          });
                      });
                  });
              });
          });
      });
    console.log("<<[PlanetaryOptimizationPageComponent.ngOnInit]");
  }
  public getTargetName(): string {
    if (null != this.targetLocation) return this.targetLocation.getName();
    else return "-WAITING-";
  }
  public getViewer(): PlanetaryOptimizationPageComponent {
    return this;
  }
}
