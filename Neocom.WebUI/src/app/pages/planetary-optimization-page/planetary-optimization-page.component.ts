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
import { NeoComError } from '../../classes/NeoComError';
import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
import { Render } from '../../models/Render.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';
import { Manager } from '../../models/Manager.model';
import { ProcessingAction } from '../../models/ProcessingAction.model';
import { PlanetaryManager } from '../../models/PlanetaryManager.model';

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

  constructor(private appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.PLANETARYOPTIMIZATION)
  }

  ngOnInit() {
    console.log(">>[PlanetaryOptimizationPageComponent.ngOnInit]");
    this.downloading = true;
    let _characterid = null;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((loginname: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.accessLoginById(loginname);
        // Check that we have a Valid login selected.
        if (null == this.appModelStore.accessLoginById(loginname)) {
          // Move the page back to the Login List.
          this.router.navigate(['/login']);
          this.exceptionList.push(new NeoComError({ message: "Login identifier " + loginname + " not found. Cannot select that login" }));
        }
      });
    // Extract also the Pilot Identifier.
    this.route.params.map(p => p.id)
      .subscribe((characterid: number) => {
        _characterid = characterid;
        // Set the login at the Service to update the other data structures.
        this.pilot = this.appModelStore.accessLogin().accessCharacterById(characterid);
        if (null == this.pilot) {
          // Retry the download of the roaster and then select the Pilot.
          this.appModelStore.accessLogin().accessPilotRoaster(this.appModelStore)
            .subscribe((roaster: NeoComCharacter[]) => {
              // Store the result on the select Login.
              this.appModelStore.accessLogin().setPilotRoaster(roaster);
              // Retry the character selection after the update of the roaster.
              this.pilot = this.appModelStore.accessLogin().accessCharacterById(characterid);
              if (null == this.pilot) {
                // Failed after the new roaster download. Cannot recover rom this error.
                this.router.navigate(['/login', this.appModelStore.accessLogin().getLoginId(), 'pilotroaster']);
                this.exceptionList.push(new NeoComError({ message: "Pilot identifier " + characterid + " not found. Cannot select that Character" }));
                return;
              }
              //    this.pilot = this.appModelStore.accessLogin().accessCharacterById(characterid);
              // Character accessed. Update it at the core Service.
              this.appModelStore.setPilotById(_characterid);
              // Get the list of Managers that can be accessed for this Character. If the list is not available thenrequest it again to the backend.
              this.pilot.accessPilotManagers(this.appModelStore)
                .subscribe(result => {
                  console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.accessPilotManagers]>ManagerList: ");
                  // Store the returned list into the current Character.
                  this.pilot.storePilotManagers(result);
                  // Search for the Planetary Manager from the list of Managers. There is no way to complete it differently.
                  for (let manager of result) {
                    if (manager.jsonClass == "PlanetaryManager") {
                      // We have found the Planetary Manager. Call the backend to optimize that list.
                      this.planetaryManager = new PlanetaryManager(manager);
                      this.route.params.map(p => p.locationid)
                        .subscribe((locationid: number) => {
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
        } else {
          // Character accessed. Update it at the core Service.
          this.appModelStore.setPilotById(_characterid);
          // Get the list of Managers that can be accessed for this Character. If the list is not available then request it again to the backend.
          this.pilot.accessPilotManagers(this.appModelStore)
            .subscribe(result => {
              console.log("--[PlanetaryOptimizationPageComponent.ngOnInit.accessPilotManagers]>ManagerList: ");
              // Store the returned list into the current Character.
              this.pilot.storePilotManagers(result);
              // Search for the Planetary Manager from the list of Managers. There is no way to complete it differently.
              for (let manager of result) {
                if (manager.jsonClass == "PlanetaryManager") {
                  // We have found the Planetary Manager. Call the backend to optimize that list.
                  this.planetaryManager = new PlanetaryManager(manager);
                  this.route.params.map(p => p.locationid)
                    .subscribe((locationid: number) => {
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
        }
      });
    console.log("<<[PilotDetailPageComponent.ngOnInit]");
    this.adapterViewList.push(new ProcessingAction());
  }
  public getTargetLocation(): number {
    return 60014089;
  }
  /**
  Indicates the viewer container that the model states have changed and that a new Collaborate2View should be executed to generate the new view list.
  */
  // public refreshViewPort(): void {
  //   this.downloading = true;
  //   // Get the list of Managers that can be accessed for this Character.
  //   this.pilot.accessPlanetaryManager(this.appModelStore)
  //     .subscribe(result => {
  //       console.log("--[PilotDetailPageComponent.ngOnInit.accessPilotRoaster]>ManagerList: " + JSON.stringify(result));
  //       // Store the result at the PIlot.
  //       // let planetary = result[0];
  //       // this.pilot.setPlanetaryManager(planetary);
  //       // Call the collaboration methods to start the model view list.
  //       let thelist = result.collaborate2View(this.getVariant());
  //       this.adapterViewList = thelist;
  //       this.downloading = false;
  //     });
  //
  // }
  public getViewer(): PlanetaryOptimizationPageComponent {
    return this;
  }
}
