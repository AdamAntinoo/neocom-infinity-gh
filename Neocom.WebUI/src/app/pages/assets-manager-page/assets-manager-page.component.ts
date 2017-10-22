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

@Component({
  selector: 'neocom-assets-manager-page',
  templateUrl: './assets-manager-page.component.html',
  styleUrls: ['./assets-manager-page.component.css']
})
export class AssetsManagerPageComponent extends PageComponent implements OnInit {
  public adapterViewList: NeoComNode[] = [];
  public downloading: boolean = true;
  public pilot: NeoComCharacter;

  constructor(private appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.ASSETSMANAGER);
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
                    this.appModelStore.setPilotById(characterid);
                    this.pilot.accessPilotDetailed(this.appModelStore)
                      .subscribe(result => {
                        console.log("--[PlanetaryManagerPageComponent.ngOnInit.accessPilotDetailed]");
                        // Copnserve the current Login reference.
                        result.setLoginReference(this.pilot.getLoginReference());
                        this.pilot = result;
                        // Download the managers.
                        this.pilot.accessPilotManagers(this.appModelStore)
                          .subscribe(result => {
                            console.log("--[PlanetaryManagerPageComponent.ngOnInit.accessPilotManagers]");
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
                            this.pilot.accessAssetsManager(this.appModelStore)
                              .subscribe(result => {
                                if (null != result) {
                                  if (result.jsonClass == "AssetsManager") {
                                    let assetman = new AssetsManager(result);
                                    // Store back this at the pilot if we have received a new download.
                                    this.pilot.setAssetsManager(assetman);
                                    // Setup the callback trap.
                                    this.appModelStore.setCallbackViewer(this);
                                    let thelist = assetman.collaborate2View(this.appModelStore, this.getVariant());
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
      });
    console.log("<<[PlanetaryManagerPageComponent.ngOnInit]");
  }
  /**
  Indicates the viewer container that the model states have changed and that a new Collaborate2View should be executed to generate the new view list.
  */
  public refreshViewPort(): void {
    this.downloading = true;
    // Get the list of Managers that can be accessed for this Character.
    this.pilot.accessAssetsManager(this.appModelStore)
      .subscribe(result => {
        if (null != result) {
          if (result.jsonClass == "AssetsManager") {
            let assetman = new AssetsManager(result);
            // Store back this at the pilot if we have received a new download.
            this.pilot.setAssetsManager(assetman);
            let thelist = assetman.collaborate2View(this.appModelStore, this.getVariant());
            this.adapterViewList = thelist;
            this.downloading = false;
          }
        }
      });

  }
  public getViewer(): AssetsManagerPageComponent {
    return this;
  }
  public getCharacterName(): string {
    if (null != this.pilot) return " - " + this.pilot.getName();
    else return "";
  }
}
