// - DOMAIN
import { Alliance } from './Alliance.domain';

export class Corporation {
    public corporationId: number = -1.0;
    public name: string = "-NAME-";
    public ticker: string = "TICK";
    public memberCount: number = 0;
    public allianceId: number = -5;
    public url4Icon: string = "http://image.eveonline.com/Corporation/1427661573_64.png";
    public corporation: boolean = true;

    public alliance: Alliance = new Alliance();
    // public ceo: Pilot = new Pilot()
    public homeStation: Location = new Location();

    public getIconUrl(): string {
      return this.url4Icon;
    }
}
