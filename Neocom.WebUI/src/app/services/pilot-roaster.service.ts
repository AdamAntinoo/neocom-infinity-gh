import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
//--- SERVICES
//import { AppCoreDataService }                 from '../services/app-core-data.service';
//--- HTTP PACKAGE
import { Http } from '@angular/http';
import { Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- MODELS
import { Pilot } from '../models/Pilot.model';

@Injectable()
export class PilotRoasterService {
  static APPLICATION_SERVICE_PORT = "9000";
  static RESOURCE_SERVICE_URL: string = "http://localhost:" + PilotRoasterService.APPLICATION_SERVICE_PORT + "/api/v1";
  private pilotRoaster: Pilot[] = [];
  private currentPilot: Pilot;

  constructor(private http: Http) { }
  //--------------------------------------------------------------------------------
  // getAllResourceLists()
  //
  // Return the list of all pilots defined on the list of API keys associated to this session. When the user
  // logs in it will generate a new session and it being populated with the set of keys defines and authorized
  // for that user. With that list the backend service can get the complete list of pilots available to
  // this account.
  //
  // Returns a llist of Pilot records with all the eve character identification information.
  public getAllPilots() {
    console.log("><[PilotRoasterService.getAllPilots]");
    return this.http.get(PilotRoasterService.RESOURCE_SERVICE_URL + "/pilotroaster")
      .map(res => res.json())
      .map(result => {
        for (let pilot of result) {
          let newpilot = new Pilot(pilot);
          this.pilotRoaster.push(newpilot);
        }
        return this.pilotRoaster;
      });
  }
  //--------------------------------------------------------------------------------
  public getPilotDetails(pilotId) {
    console.log("><[PilotRoasterService.getPilotDetails]");
    return this.http.get(PilotRoasterService.RESOURCE_SERVICE_URL + "/pilot/" + pilotId)
      .map(res => res.json())
      .map(result => {
        //    for (let pilot of result) {
        let newpilot = new Pilot(result);
        this.currentPilot = newpilot;
        //    }
        return this.currentPilot;
      });
  }
}
