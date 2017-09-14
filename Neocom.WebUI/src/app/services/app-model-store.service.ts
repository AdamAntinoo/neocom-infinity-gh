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
//--- INTERFACES
import { IDataSource } from '../classes/IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from '../classes/DataSourceLocator';
//--- MODELS
import { Login } from '../models/Login.model';
import { Render } from '../models/Render.model';
import { Pilot } from '../models/Pilot.model';

//
// This service handles the application storage of elements required to setup the data
// hierarchy while generating the presentations. One of the additional elements it
// stores besides the Android equivalent is the DataSource associated to the ListView.
//
@Injectable()
export class AppModelStoreService {
  private loginList: Login[] = null;
  private currentLogin: string = "Default";

  private dataSourceCache: IDataSource[] = [];
  private _activeDataSource: IDataSource = null;
  private _viewList: Observable<Array<Render>>;

  constructor() { }
  /**
  Sets the new login that comes from the URL when the user selects one from the list of logins.
  If the Login set is different from the current Login then we fire the download of
  the list of Pilots associated with that Login's Keys.
  */
  public setLogin(newlogin: string): void {
    this.currentLogin = newlogin;
  }
  public accessLogin(): string {
    return this.currentLogin;
  }

  /**
  Go to the backend Database to retrieve the list of declared Logins to let the
  user to select the one he/she wants for working. If the list is already
  downloaded then do not access again the Database and return the cached list.
  */
  public accessLoginList(): Observable<Login[]> {
    console.log(">>[AppModelStoreService.accessLoginList]");
    if (null == this.loginList) {
      // Get the list form the backend Database.
      // On this preliminar version simulate it with a hand made list.
      this.loginList = [];
      this.loginList.push(new Login({ loginid: "Beth" }));
      this.loginList.push(new Login({ loginid: "Perico" }));
      this.loginList.push(new Login({ loginid: "CapitanHaddock09" }));
    }
    return new Observable(observer => {
      setTimeout(() => {
        observer.next(this.loginList);
      }, 100);
      setTimeout(() => {
        observer.complete();
      }, 100);
    });
  }

  public accessDataSource(): IDataSource {
    return this._activeDataSource;
  }
  public searchDataSource(locator: DataSourceLocator): IDataSource {
    let target = this.dataSourceCache[locator.getLocator()];
    return target;
  }



  /**
  Checks if this datasource is already present at the registration list. If found returns the already found datasource, otherwise adds this to the list of caches datasources.
*/
  public registerDataSource(ds: IDataSource): IDataSource {
    let locator = ds.getLocator();
    let target = this.dataSourceCache[locator.getLocator()];
    if (target == null) {
      this.dataSourceCache.push(ds);
      return ds;
      // If this new datasource is added to the cache then ativate the initial model hierarchy.
      // Call the background service to get the model contents.
      //  return ds.collaborate2Model();
      // } else {
      //   return new Promise((resolve, reject) => {
      //     resolve(target.collaborate2View());
      //   });
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
    return target;
  }
  public setActiveDataSource(ds: IDataSource): IDataSource {
    this._activeDataSource = ds;
    return this._activeDataSource;
  }
}
