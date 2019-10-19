// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class Credential extends NeoCom {
    private uniqueId: string;
    private accountId: number;
    private accountName: string;
    private dataSource: string;
    // private corporationId: number;
    private assetsCount: number = 0;
    private walletBalance: number = 0;
    private miningResourcesEstimatedValue: number = 0;
    private raceName: string;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = 'Credential';
    }

    public getAccountId(): number {
        return this.accountId;
    }
    public getAccountName(): string {
        return this.accountName;
    }
    public getUniqueId(): string {
        return this.uniqueId;
    }
    public getRaceName(): string {
        if (null != this.raceName) return this.raceName;
        else return '-UNDETERMINED-';
    }
    // public getCorporationId(): number {
    //     return this.corporationId;
    // }
}
