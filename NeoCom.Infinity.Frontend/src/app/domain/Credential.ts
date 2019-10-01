export class Credential {
    private uniqueId: string;
    private accountId: number;
    private accountName: string;
    private dataSource: string;

    constructor(values: Object = {}) {
        // super(values);
        Object.assign(this, values);
    }
    public getAccountName(): string {
        return this.accountName;
    }
    public getUniqueId(): string {
        return this.uniqueId;
    }
}
