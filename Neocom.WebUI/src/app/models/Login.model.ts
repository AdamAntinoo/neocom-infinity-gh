import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComCharacter } from '../models/NeoComCharacter.model';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';

export class Login extends Render {
  public loginid: string = "-ID-";
  private downloadPending: boolean = false;
  //  public keyCount: number = -1;
  private _downloaded: boolean = false;
  private _pilotRoaster: NeoComCharacter[] = null;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Login";
    this.downloadPending = false;
  }

  public getLoginId(): string {
    return this.loginid;
  }
  public getPanelIcon(): string {
    return "login.png";
  }
  /**
  Gets the number of Characters associated to this Login. This attribute has no value until the Login is selected and then the page changes. So to give it a little of functionality it should fire the download when the data is not present.
  But the task is not easy since the class has no access to any of the Services. It should have a Service sent by parameter to be able to complete the operation.
  */
  public getKeyCount(): number {
    if (this._downloaded)
      return this._pilotRoaster.length;
    else return 0;
  }
  public getKeyCountObsrver(downloadService: AppModelStoreService): number {
    if (this._downloaded) return this._pilotRoaster.length;
    else {
      if (this.downloadPending) return 0;
      else {
        this.downloadPending = true;
        // Get the pilot roaster and then calculate the count.
        downloadService.getBackendPilotRoaster(this.getLoginId())
          .subscribe(result => {
            console.log("--[Login.getKeyCount.getBackendPilotRoaster]>Roaster: " + JSON.stringify(result));
            // The the list of planetary resource lists to the data returned.
            this._pilotRoaster = result;
            this._downloaded = true;
            this.downloadPending = false;
            return this._pilotRoaster.length;
          });
      }
    }
  }
  public accessPilotRoaster(downloadService: AppModelStoreService): Observable<Render[]> {
    if (this._downloaded)
      return new Observable(observer => {
        setTimeout(() => {
          observer.next(this._pilotRoaster);
        }, 100);
        setTimeout(() => {
          observer.complete();
        }, 100);
      });
    else
      return downloadService.getBackendPilotRoaster(this.getLoginId());
  }
  /**
  Search the Character by its id and then select if as the current character for next operations.
  */
  public accessCharacterById(id: number): NeoComCharacter {
    if (null != this._pilotRoaster) {
      for (let c of this._pilotRoaster) {
        if (c.getId() == id) return c;
      }
      throw new TypeError("Character identifier " + id + " not found. Cannot select that NeoComCharacter");
    }
  }
}
