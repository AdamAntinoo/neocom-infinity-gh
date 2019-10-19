// - DOMAIN
import { Alliance } from './Alliance.domain';
import { Pilot } from './Pilot.domain';
import { NeoCom } from './NeoCom.domain';

export class Corporation extends NeoCom {
   public corporationId: number;
   public name: string = "-NAME-";
   public ticker: string = "TICK";
   public member_count: number = 0;
   public allianceId: number;
   public url4Icon: string;

   public ceoPilotData: Pilot;
   public alliance: Alliance;
   public home_station_id: number;
   // public homeStation: Location = new Location();

   constructor(values: Object = {}) {
      super();
      Object.assign(this, values);
      // Transform child instances to class objects.
      if (null != this.ceoPilotData) this.ceoPilotData = new Pilot(this.ceoPilotData);
      if (null != this.alliance) this.alliance = new Alliance(this.alliance);
   }

   public getIconUrl(): string {
      if (null != this.url4Icon) return this.url4Icon;
      else return '/assets/res-sde/drawable/corporation.png';
   }
}
