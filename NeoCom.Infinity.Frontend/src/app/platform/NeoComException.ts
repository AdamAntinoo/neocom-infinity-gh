// - DOMAIN
import { NeoCom } from '@app/domain/NeoCom.domain';

export class NeoComException extends NeoCom {
    private status: number = 0;
    private statusText: string = "Undefined Exception";
    private allowsRetry: boolean = false;
    private message: string = "Request undefined exception:0:Undefined Exception.";
    private userMessage: string;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
    }
    /** If the user message is not defined the we can expect to use the exception message. */
    public getUserMessage(): string {
        if (this.isEmpty(this.userMessage)) return this.message;
        else return this.userMessage;
    }
    public setUserMessage(message: string): NeoComException {
        this.userMessage = message;
        return this;
    }
    public setRetryable(retry: boolean): NeoComException {
        this.allowsRetry = retry;
        return this;
    }
}
