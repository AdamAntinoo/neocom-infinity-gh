import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
import { ESeparator } from '../../classes/ESeparator.enumerated';
//--- CLASSES
//import { PilotListDataSource } from '../../classes/PilotListDataSource';
import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Login } from '../../models/Login.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';
import { Manager } from '../../models/Manager.model';
import { AssetsManager } from '../../models/AssetsManager.model';
import { PlanetaryManager } from '../../models/PlanetaryManager.model';
import { Separator } from '../../models/Separator.model';

@Component({
  selector: 'neocom-pilot-detail-page',
  templateUrl: './pilot-detail-page.component.html',
  styleUrls: ['./pilot-detail-page.component.css']
})

export class PilotDetailPageComponent extends PageComponent implements OnInit {
  public adapterViewList: Manager[] = [];
  public headerSeparator: Separator = new Separator().setVariation(ESeparator.GREEN);
  public downloading: boolean = true;
  public selectedLogin: Login = null;
  public pilot: NeoComCharacter = null;

  constructor(private appModelStore: AppModelStoreService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.PILOTDETAILS)
    this.setVariant(EVariant.PILOTMANAGERS)
  }
  /**
  Gets the parametrs from the Route. With those parameters we can get access to the data requested and required to draw the page.
  */
  ngOnInit() {
    console.log(">>[PilotDetailPageComponent.ngOnInit]");
    this.downloading = true;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.accessLoginList()
          .subscribe(result => {
            console.log("--[PilotDetailPageComponent.ngOnInit.accessLoginList]");
            //    this.appModelStore.setLoginList(result);
            this.appModelStore.activateLoginById(login)
              .subscribe(result => {
                console.log("--[PilotDetailPageComponent.ngOnInit.activateLoginById]");
                // We have reached the selected Login. Search now for the character.
                this.selectedLogin = result;
                this.route.params.map(p => p.id)
                  .subscribe((characterid: number) => {
                    this.pilot = this.appModelStore.activatePilotById(characterid);
                    // Download the managers.
                    this.pilot.accessPilotManagers(this.appModelStore)
                      .subscribe(result => {
                        console.log("--[PilotDetailPageComponent.ngOnInit.accessPilotManagers]");
                        // The the list of pilot managers should be stored at the pilot.
                        this.adapterViewList = this.pilot.collaborate2View(this.appModelStore, this.getVariant());
                        this.downloading = false;
                      });
                  });
              });
          });
      });
    console.log("<<[PilotDetailPageComponent.ngOnInit]");
  }
  public getViewer(): PilotDetailPageComponent {
    return this;
  }
}
