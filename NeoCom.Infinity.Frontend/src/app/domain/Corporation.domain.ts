// - DOMAIN
import { Alliance } from './Alliance.domain';

export class Corporation {
   public corporationId: number;
   public name: string = "-NAME-";
   public ticker: string = "TICK";
   public member_count: number = 0;
   public allianceId: number;
   public url4Icon: string;
   //  public corporation: boolean = true;

   public alliance: Alliance;
   // public ceo: Pilot = new Pilot()
   public home_station_id: number;
   // public homeStation: Location = new Location();

   constructor(values: Object = {}) {
      Object.assign(this, values);
   }

   public getIconUrl(): string {
      if (null != this.url4Icon) return this.url4Icon;
      else return '/assets/res-sde/drawable/corporation.png';
   }
}
