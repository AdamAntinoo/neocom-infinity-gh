export class NeoCom {
    protected jsonClass: string = 'NeoCom';

    constructor() { }

    public getJsonClass(): string {
        return this.jsonClass;
    }
    protected isEmpty(target?: any): boolean {
        if (null == target) return true;
        if (Object.keys(target).length > 0) return false;
        return true;
    }
}
