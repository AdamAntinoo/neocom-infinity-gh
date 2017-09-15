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
  //  public keyCount: number = -1;
  private _downloaded: boolean = false;
  private _pilotRoaster: NeoComCharacter[] = null;

  constructor(values: Object = {}) {
    super(values);
    Object.assign(this, values);
    this.jsonClass = "Login";
  }
  public getLoginId(): string {
    return this.loginid;
  }
  public getPanelIcon(): string {
    return "login.png";
  }
  public getKeyCount(): number {
    if (this._downloaded) return this._pilotRoaster.length;
    else return 0;
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
