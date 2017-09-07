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
//--- INTERFACES
import { IDataSource } from '../classes/IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from '../classes/DataSourceLocator';

//--- MODELS
import { Render } from '../models/Render.model';
import { Pilot } from '../models/Pilot.model';

//
// This service handles the application storage of elements required to setup the data
// hierarchy while generating the presentations. One of the additional elements it
// stores besides the Android equivalent is the DataSource associated to the ListView.
//
@Injectable()
export class AppModelStoreService {
  private dataSourceCache: IDataSource[] = [];
  private _activeDataSource: IDataSource = null;
  private _viewList: Observable<Array<Render>>;

  constructor() { }

  public accessDataSource(): IDataSource {
    return this._activeDataSource;
  }
  public locateDataSource(locator: DataSourceLocator): IDataSource {
    let target = this.dataSourceCache[locator.getLocator()];
    return target;
  }
  // Checks if this datasource is already present at the cache. If found returns the
  // already found datasource, otherwise adds this to the list of caches datasources.
  // The return from this meythod is a Promise
  public registerDataSource(ds: IDataSource) {
    let locator = ds.getLocator();
    let target = this.dataSourceCache[locator.getLocator()];
    if (target == null) {
      this.dataSourceCache.push(ds);
      // If this new datasource is added to the cache then ativate the initial model hierarchy.
      // Call the background service to get the model contents.
      return ds.collaborate2Model();
    } else {
      return new Promise((resolve, reject) => {
        resolve(target.collaborate2View());
      });
      // this._viewList= new Observable(observer => {
      //           setTimeout(() => {
      //               observer.next(42);
      //           }, 1000);
      //
      //           setTimeout(() => {
      //               observer.next(43);
      //           }, 2000);
      //
      //           setTimeout(() => {
      //               observer.complete();
      //           }, 3000);
      //       });
      //       }
    }
  }
  public setActiveDataSource(ds: IDataSource): IDataSource {
    this._activeDataSource = ds;
    return this._activeDataSource;
  }
}
