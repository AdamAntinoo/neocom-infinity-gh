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
  static APPLICATION_SERVICE_PORT = "9000";
  static RESOURCE_SERVICE_URL: string = "http://localhost:" + PilotListDataSourceService.APPLICATION_SERVICE_PORT + "/api/v1";

  private _locator: DataSourceLocator = null;
  private _variant: EVariant = EVariant.DEFAULT;
  private _dataModelRoot: NeoComNode[] = [];
  private _viewModelRoot: Render[] = [];

  constructor(private http: Http, private cookieService: CookieService, private appModelStore: AppModelStoreService) { }

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

  public collaborate2Model(): Observable<NeoComNode[]> {
    this.cookieService.put("login-id", "default")
    let pilots = this.getAllPilots();
    //  let pro = pilots.toPromise();
    return pilots;
  }
  public collaborate2View(): Render[] {
    let r = new Region({ name: "Region 1" });
    this._viewModelRoot.push(r);
    return this._viewModelRoot;
  }

  private getAllPilots() {
    console.log("><[PilotListDataSourceService.getAllPilots]");
    return this.http.get(PilotListDataSourceService.RESOURCE_SERVICE_URL + "/pilotroaster")
      .map(res => res.json())
      .map(result => {
        for (let pilot of result) {
          let newpilot = new Pilot(pilot);
          this._dataModelRoot.push(newpilot);
        }
        return this._dataModelRoot;
      });
  }

}
