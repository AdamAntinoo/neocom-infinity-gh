// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class TabDefinition extends NeoCom {
    public name: string;
    public icon: string;
    public pageLink: string;
    public active: boolean = false;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'TabDefinition';
    }

    public getName(): string {
        return this.name;
    }
    public isActive(): boolean {
        return this.active;
    }
    public select(): void {
        this.active = true;
    }
}
