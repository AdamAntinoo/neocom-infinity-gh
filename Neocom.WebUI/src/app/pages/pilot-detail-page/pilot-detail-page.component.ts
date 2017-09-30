import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
//import { PilotRoasterService } from '../../services/pilot-roaster.service';
import { AppModelStoreService } from '../../services/app-model-store.service';
//import { PilotListDataSourceService } from '../../services/pilot-list-data-source.service';
//import { PilotManagersDataSourceService } from '../../services/pilot-managers-data-source.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
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

@Component({
  selector: 'neocom-pilot-detail-page',
  templateUrl: './pilot-detail-page.component.html',
  styleUrls: ['./pilot-detail-page.component.css']
})

export class PilotDetailPageComponent extends PageComponent implements OnInit {
  public adapterViewList: Manager[] = [];
  public downloading: boolean = true;
  public login: Login = null;
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
    // let _characterid = null;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.accessLoginList()
          .subscribe(result => {
            console.log("--[PilotDetailPageComponent.ngOnInit.accessLoginList]");
            this.appModelStore.setLoginList(result);
            this.appModelStore.activateLoginById(login)
              .subscribe(result => {
                console.log("--[PilotDetailPageComponent.ngOnInit.activateLoginById]");
                // We have reached the selected Login. Search now for the character.
                let selectedLogin = result;
                this.route.params.map(p => p.id)
                  .subscribe((characterid: number) => {
                    this.pilot = selectedLogin.accessCharacterById(characterid);
                    this.pilot.accessPilotDetailed(this.appModelStore)
                      .subscribe(result => {
                        console.log("--[PilotDetailPageComponent.ngOnInit.activateLoginById.accessPilotManagers]");
                        // Copnserve the current Login reference.
                        result.setLoginReference(this.pilot.getLoginReference());
                        this.pilot = result;
                        // The the list of planetary resource lists to the data returned.
                        this.adapterViewList = this.pilot.getManagers();
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
