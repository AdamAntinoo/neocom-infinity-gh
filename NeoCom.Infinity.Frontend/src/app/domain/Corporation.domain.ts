// - DOMAIN
import { Alliance } from './Alliance.domain';
import { Pilot } from './Pilot.domain';
import { NeoCom } from './NeoCom.domain';

export class Corporation extends NeoCom {
    public corporationId: number;
    public corporationPublicData: any;
    public ceoPilotData: Pilot;
    public allianceId: number;
    public alliance: Alliance;
    public url4Icon: string;
    // public homeStation: Location = new Location();

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'Corporation';
        // Transform child instances to class objects.
        if (null != this.ceoPilotData) this.ceoPilotData = new Pilot(this.ceoPilotData);
        if (null != this.alliance) this.alliance = new Alliance(this.alliance);
    }
    public getName(): string {
        if (null != this.corporationPublicData) return this.corporationPublicData.name;
        else return '-';
    }
    public getTicker(): string {
        if (null != this.corporationPublicData) return this.corporationPublicData.ticker;
        else return '----';
    }
    public getMemberCount(): number {
        if (null != this.corporationPublicData) return this.corporationPublicData.member_count;
        else return 0;
    }
    public getAlliance(): Alliance {
        return this.alliance;
    }
    public getIconUrl(): string {
        if (null != this.url4Icon) return this.url4Icon;
        else return '/assets/res-sde/drawable/corporation.png';
    }
}
