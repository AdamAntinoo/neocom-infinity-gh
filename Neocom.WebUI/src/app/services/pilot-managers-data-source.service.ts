import { Injectable } from '@angular/core';
import { Inject } from '@angular/core';
import { CookieService } from 'ngx-cookie';
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
import { Manager } from '../models/Manager.model';

@Injectable()
export class PilotManagersDataSourceService extends AbstractDataSource implements IDataSource {
  //  static SERVICE_NAME = "PilotManagersDataSource";

  constructor(private http: Http, private cookieService: CookieService, private appModelStore: AppModelStoreService) {
    super();
    this._serviceName = "PilotManagersDataSource";
  }

  /**
  This method is called whenever the page needs to render. It has two phases, the first one will check if we require a refresh of the model, depending on some properties. The second one will create the view contents list from the current model elements by recursively calling their 'collaborate2View()' method.
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

    // Get again the model from the backend service.
    return this.getAllManagers();
  }
  private getAllManagers(): Observable<Render[]> {
    console.log("><[PilotManagersDataSourceService.getAllManagers]");
    this.cookieService.put("login-id", "default")
    return this.http.get(AbstractDataSource.RESOURCE_SERVICE_URL + "/pilotmanagers/" + "92223647")
      .map(res => res.json())
      .map(result => {
        for (let manager of result) {
          let newmanager = new Manager(manager);
          this._dataModelRoot.push(newmanager);
        }
        this._downloaded = true;
        return this.processModel();
      });
  }

}
