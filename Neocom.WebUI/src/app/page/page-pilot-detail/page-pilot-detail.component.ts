import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';

//--- SERVICES
import { PilotRoasterService } from '../../services/pilot-roaster.service';
//--- MODELS
import { Pilot } from '../../models/Pilot.model';

@Component({
  selector: 'neocom-page-pilot-detail',
  templateUrl: './page-pilot-detail.component.html',
  styleUrls: ['./page-pilot-detail.component.css']
})
export class PagePilotDetailComponent implements OnInit {
  private pilot: Pilot;
  constructor(private route: ActivatedRoute, private router: Router, private pilotRoasterService: PilotRoasterService) { }

  ngOnInit() {
    this.route.paramMap
      .switchMap((params: ParamMap) =>
        this.pilotRoasterService.getPilotDetails(params.get('id')))
      .subscribe((pilot: Pilot) => this.pilot = pilot);
  }
}
