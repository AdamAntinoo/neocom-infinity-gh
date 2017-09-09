import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
import { PilotRoasterService } from '../../services/pilot-roaster.service';
import { AppModelStoreService } from '../../services/app-model-store.service';
import { PilotListDataSourceService } from '../../services/pilot-list-data-source.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- CLASSES
//import { PilotListDataSource } from '../../classes/PilotListDataSource';
import { DataSourceLocator } from '../../classes/DataSourceLocator';
//--- MODELS
import { Render } from '../../models/Render.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { Pilot } from '../../models/Pilot.model';
import { Region } from '../../models/Region.model';

@Component({
  selector: 'neocom-pilot-detail-page',
  templateUrl: './pilot-detail-page.component.html',
  styleUrls: ['./pilot-detail-page.component.css']
})

export class PilotDetailPageComponent implements OnInit {
  public downloading: boolean = true;
  public completed: boolean = false;
  public adapterViewList: Render[] = [];

  public pilot: Pilot;
  constructor(private route: ActivatedRoute, private router: Router, private pilotRoasterService: PilotRoasterService) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) =>
        this.pilotRoasterService.getPilotDetails(params.get('id')))
      .subscribe((pilot: Pilot) => {
        this.pilot = pilot;
        this.completed = true;
        this.downloading = false;
      });
  }
}
