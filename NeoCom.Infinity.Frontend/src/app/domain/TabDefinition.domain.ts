// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class TabDefinition extends NeoCom {
    public name: string;
    public icon: string;
    public pageLink: string;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'TabDefinition';
    }
}
