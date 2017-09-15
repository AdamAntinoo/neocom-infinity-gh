import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
//import { CookieService } from 'ngx-cookie';
//--- HTTP PACKAGE
import { Http } from '@angular/http';
import { Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

//--- SERVICES
import { AppModelStoreService } from './app-model-store.service';
//--- INTERFACES
import { IDataSource } from '../classes/IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from '../classes/DataSourceLocator';
import { EVariant } from '../classes/EVariant.enumerated';
import { AbstractDataSource } from '../classes/AbstractDataSource';
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { Pilot } from '../models/Pilot.model';
import { Region } from '../models/Region.model';

@Injectable()
export class PilotListDataSourceService extends AbstractDataSource implements IDataSource {

  constructor(private appModelStore: AppModelStoreService) {
    super();
    this._serviceName = "PilotListDataSource";
    this._variant = EVariant.PILOTMANAGERS;
    this._canBeCached = true;
    this._downloaded = false;
  }

  /**
  This method is called whenever the page needs to get the list of nodes that are on the view and ready for rendering. New DataSource model will relay all data requests to the AppModel Service that is the general cache for all application data. Once we get the right data depending on the Variant we can process it to the view list based on the creation of the view contents list from the current model elements by recursively calling their 'collaborate2View()' method.
  */
  public collaborate2View(): Observable<Render[]> {
    // Check if model needs to be refreshed.
    if (this._canBeCached) {
      if (this._downloaded) {
        // The model can be reused. Return a new generated view list.
        return new Observable(observer => {
          setTimeout(() => {
            observer.next(this.processModel());
          }, 100);
          setTimeout(() => {
            observer.complete();
          }, 100);
        });
      }
    }

    // Get the model data from the AppModelStore Service.
    let result = this.appModelStore.accessLogin().accessPilotRoaster(this.appModelStore);
    this._downloaded = true;
    return result;
  }

  // private getAllPilots(): Observable<Render[]> {
  //   console.log("><[PilotListDataSourceService.getAllPilots]");
  //   //  this.cookieService.put("login-id", "default")
  //   return this.http.get(PilotListDataSourceService.RESOURCE_SERVICE_URL + "/login/" + this.appModelStore.accessLogin() + "/pilotroaster")
  //     .map(res => res.json())
  //     .map(result => {
  //       for (let pilot of result) {
  //         let newpilot = new Pilot(pilot);
  //         this._dataModelRoot.push(newpilot);
  //       }
  //       this._downloaded = true;
  //       return this.processModel();
  //     });
  // }
}
