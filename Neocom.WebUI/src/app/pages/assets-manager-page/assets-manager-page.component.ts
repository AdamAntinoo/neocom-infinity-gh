import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- CLASSES
//import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
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
    console.log(">>[AssetsManagerPageComponent.ngOnInit]");
    this.downloading = true;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.accessLoginList()
          .subscribe(result => {
            console.log("--[AssetsManagerPageComponent.ngOnInit.accessLoginList]");
            this.appModelStore.activateLoginById(login)
              .subscribe(result => {
                console.log("--[AssetsManagerPageComponent.ngOnInit.activateLoginById]");
                // We have reached the selected Login. Search now for the character.
                let selectedLogin = result;
                this.route.params.map(p => p.id)
                  .subscribe((characterid: number) => {
                    this.pilot = this.appModelStore.activatePilotById(characterid);
                    // Use the same refresg to get access to the managers and then collaborate the Assets manager
                    this.refreshViewPort();
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
    this.pilot.accessPilotManagers(this.appModelStore)
      .subscribe(result => {
        console.log("--[AssetsManagerPageComponent.ngOnInit.accessPilotManagers]");
        // Access the Assets manager that should be already available at the Character
        this.appModelStore.setCallbackViewer(this);
        let manager = this.pilot.getAssetsManager();
        if (null != manager) {
          this.adapterViewList = manager.collaborate2View(this.appModelStore, this.getVariant());
          this.downloading = false;
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
