import { Component, OnInit }  from '@angular/core';
import { CookieService }      from 'ngx-cookie';

//--- SERVICES
import { PilotRoasterService } from '../../services/pilot-roaster.service';
//--- MODELS
import { Pilot } from '../../models/Pilot';

@Component({
  selector: 'neocom-page-pilots',
  templateUrl: './page-pilots.component.html',
  styleUrls: ['./page-pilots.component.css']
})
export class PagePilotsComponent implements OnInit {
  public pilotList: Pilot[] = [];

  constructor(private pilotRoasterService: PilotRoasterService, private _cookieService: CookieService) { }

  ngOnInit() {
    // Download the list fo pilots from the backend service.
    console.log("--[PagePilotsComponent.ngOnInit]> firing getPilotRoaster");
    // Define the cookie and initialize it to the login predefined value.
    this._cookieService.put("login-id", "default")
    this.pilotRoasterService.getAllPilots()
      .subscribe(result => {
        console.log("--[PagePilotsComponent.ngOnInit.subscribe]> pilot list: " + JSON.stringify(result));
        // The the list of planatary resource lists to the data returned.
        this.pilotList = result;
      });
  }
  public collaborate2Model() {
    return this.pilotList;
  }
}
