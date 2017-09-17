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
  public pilot: NeoComCharacter;

  //  public completed: boolean = false;
  //  private selectedId;

  constructor(private appModelStore: AppModelStoreService, private pilotManagerService: PilotManagersDataSourceService, private route: ActivatedRoute, private router: Router) {
    super();
    this.setVariant(EVariant.PILOTMANAGERS)
  }
  /**

  */
  ngOnInit() {
    console.log(">>[PilotDetailPageComponent.ngOnInit]");
    this.downloading = true;
    let _characterid = null;
    // Extract the login identifier from the URL structure.
    this.route.params.map(p => p.loginid)
      .subscribe((login: string) => {
        // Set the login at the Service to update the other data structures. Pass the login id
        this.appModelStore.setLoginById(login);
        // Check that we have a Valid login selected.
        if (null == this.appModelStore.accessLogin()) {
          // Move the page back to the Login List.
          this.router.navigate(['/login']);
        }
      });
    // Extract also the Pilot Identifier.
    this.route.params.map(p => p.id)
      .subscribe((characterid: number) => {
        _characterid = characterid;
        // Set the login at the Service to update the other data structures.
        if (null == this.appModelStore.accessLogin().accessCharacterById(characterid)) {
          // Retry the download of the roaster and then select the Pilot.
          this.appModelStore.accessLogin().accessPilotRoaster(this.appModelStore)
            .subscribe((roaster: NeoComCharacter[]) => {
              this.appModelStore.accessLogin().setPilotRoaster(roaster);
              if (null == this.appModelStore.accessLogin().accessCharacterById(characterid)) {
                this.router.navigate(['/login', this.appModelStore.accessLogin().getLoginId(), 'pilotroaster']);
              }
              this.pilot = this.appModelStore.accessLogin().accessCharacterById(characterid);
              // Get the list of Managers that can be accessed for this Character.
              this.appModelStore.accessLogin()
                .accessCharacterById(characterid)
                .accessPilotManagers(this.appModelStore)
                .subscribe(result => {
                  console.log("--[PilotDetailPageComponent.ngOnInit.accessPilotRoaster]>ManagerList: " + JSON.stringify(result));
                  // The the list of planetary resource lists to the data returned.
                  this.adapterViewList = result;
                  this.downloading = false;
                });
            });
        } else {
          this.pilot = this.appModelStore.accessLogin().accessCharacterById(characterid);
          // Get the list of Managers that can be accessed for this Character.
          this.appModelStore.accessLogin()
            .accessCharacterById(characterid)
            .accessPilotManagers(this.appModelStore)
            .subscribe(result => {
              console.log("--[PilotDetailPageComponent.ngOnInit.accessPilotRoaster]>ManagerList: " + JSON.stringify(result));
              // The the list of planetary resource lists to the data returned.
              this.adapterViewList = result;
              this.downloading = false;
            });
        }
      });

    console.log("<<[PilotDetailPageComponent.ngOnInit]");
  }
}
