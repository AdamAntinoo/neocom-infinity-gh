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
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { Pilot } from '../models/Pilot.model';
import { Region } from '../models/Region.model';

@Injectable()
export class PilotListDataSourceService implements IDataSource {
  static SERVICE_NAME = "PilotListDataSource";
  static APPLICATION_SERVICE_PORT = "9000";
  static RESOURCE_SERVICE_URL: string = "http://localhost:" + PilotListDataSourceService.APPLICATION_SERVICE_PORT + "/api/v1";

  private _canBeCached: boolean = true;
  private _downloaded: boolean = false;
  private _locator: DataSourceLocator = null;
  private _variant: EVariant = EVariant.DEFAULT;
  private _dataModelRoot: NeoComNode[] = [];
  private _viewModelRoot: Render[] = [];

  constructor(private http: Http, private cookieService: CookieService, private appModelStore: AppModelStoreService) {
    this._variant = EVariant.PILOTMANAGERS;
  }

  public getLocator(): DataSourceLocator {
    return this._locator;
  }
  public getVariant(): EVariant {
    return this._variant;
  }
  public getVariantName(): string {
    return EVariant[this._variant];
  }
  public setLocator(locator: DataSourceLocator): void {
    this._locator = locator;
  }
  public setVariant(variant: EVariant): void {
    this._variant = variant;
  }
  public getServiceName(): string {
    return PilotListDataSourceService.SERVICE_NAME;
  }

  // public collaborate2Model(): Observable<NeoComNode[]> {
  //   this.cookieService.put("login-id", "default")
  //   let pilots = this.getAllPilots();
  //   //  let pro = pilots.toPromise();
  //   return pilots;
  // }
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
    return this.getAllPilots();
  }

  private getAllPilots(): Observable<Render[]> {
    console.log("><[PilotListDataSourceService.getAllPilots]");
    this.cookieService.put("login-id", "default")
    return this.http.get(PilotListDataSourceService.RESOURCE_SERVICE_URL + "/pilotroaster")
      .map(res => res.json())
      .map(result => {
        for (let pilot of result) {
          let newpilot = new Pilot(pilot);
          this._dataModelRoot.push(newpilot);
        }
        this._downloaded = true;
        return this.processModel();
      });
  }

  /** Read all the model nodes and generate a new list of their collaborations to the view list.
  */
  private processModel(): Render[] {
    this._viewModelRoot = null;
    for (let node of this._dataModelRoot) {
      let collab = node.collaborate2View(this.getVariant());
      if (null === this._viewModelRoot) {
        this._viewModelRoot = collab;
      } else {
        this._viewModelRoot.concat(collab);
      }
      console.log("><[PilotListDataSourceService.processModel]");
    }
    return this._viewModelRoot;
  }
}
