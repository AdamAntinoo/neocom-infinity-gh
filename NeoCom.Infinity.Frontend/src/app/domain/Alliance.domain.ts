// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class Alliance extends NeoCom {
    public allianceId: number = -5;
    public name: string = "-NAME-";
    public ticker: string = "TICK";
    public url4Icon: string = "http://image.eveonline.com/Alliance/117383987_64.png";

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = "Alliance";
    }

    //--- GETTERS & SETTERS
    public getId(): number {
        return this.allianceId;
    }
    public getName(): string {
        return this.name;
    }
    public getIconUrl(): string {
        return this.url4Icon;
    }
}
