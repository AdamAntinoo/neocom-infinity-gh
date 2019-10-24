// - DOMAIN
import { NeoCom } from './NeoCom.domain';
import { is } from 'date-fns/locale';
import { environment } from '@env/environment';

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
    public getName(): string {
        if (this.isEmpty(this.name)) return '-';
        return this.name;
    }
    public getPilotIcon(): string {
        if (this.isEmpty(this.url4Icon)) return environment.DEFAULT_AVATAR_PLACEHOLDER;
        else return this.url4Icon;
    }
}
