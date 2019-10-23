// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class Pilot extends NeoCom {
    public pilotId: number;
    public name: string;
    public race: any;
    public ancestry: any;
    public bloodline: any;
    public gender: string;
    public url4Icon: string;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'Pilot';
    }
}
