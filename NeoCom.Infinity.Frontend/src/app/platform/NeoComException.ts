export class NeoComException {
    private ok: boolean = false;
    private status: number = 0;
    private statusText: string = "Undefined Exception";
    private message: string = "Request undefined exception:0:Undefined Exceotion.";

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
