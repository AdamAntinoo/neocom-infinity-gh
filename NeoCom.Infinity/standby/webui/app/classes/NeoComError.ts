//--- INTERFACES
import { IPage } from './IPage.interface';
import { EVariant } from './EVariant.enumerated';

export class NeoComError implements Error {
	public message: string = "";
	public name;
	public jsonClass = "NeoComError";

	constructor(values: Object = {}) {
		Object.assign(this, values);
	}

	public getMessage(): string {
		return this.message;
	}
}
