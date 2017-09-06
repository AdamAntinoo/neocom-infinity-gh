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

//--- MODELS
import { Pilot } from '../models/Pilot';

//
// This service handles the application storage of elements required to setup the data
// hierarchy while generating the presentations. One of the additional elements it
// stores besides the Android equivalent is the DataSource associated to the ListView.
//
@Injectable()
export class AppModelStoreService {
  private dataSourceCache: IDataSource[] = [];
  private activeDataSource: IDataSource = null;
  constructor() { }

  public accessDataSource(): IDataSource {
    return this.activeDataSource;
  }
  // Checks if this datasource is already present at the cache. If found returns the
  // already found datasource, otherwise adds this to the list of caches datasources.
  public registerDataSource(ds: IDataSource) {
    let locator = ds.getLocator();
    let target = this.dataSourceCache[locator.getLocator()];
    if (target == null) {
      this.dataSourceCache.push(ds);
      target = ds;
    }
    return target;
  }
}
