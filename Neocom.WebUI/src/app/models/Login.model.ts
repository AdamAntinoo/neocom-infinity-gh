//--- MODELS
import { Render } from '../models/Render.model';

export class Login extends Render {
  public jsonClass: string = "Login";
  public loginid: string = "-ID-";
  public password: string = "";
  public keyCount: number = 0;

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
    return 2;
  }
}
