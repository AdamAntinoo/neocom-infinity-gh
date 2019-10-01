import { NeoComResponse } from './NeoComResponse';
import { Credential } from '../Credential';

export class ValidateAuthorizationTokenResponse extends NeoComResponse {
    private jwtToken: string;
    private credential: Credential;

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        // Transform dependency objects
        if (null != this.credential) {
            // let serviceList = [];
            // for (let service of this.serviciosAutorizados) {
            //     serviceList.push(new ServiceAuthorized(service));
            // }
            this.credential = new Credential(this.credential);
        }
    }
    public getJwtToken(): string {
        return this.jwtToken;
    }
    public getCredential(): Credential{
        return this.credential;
    }
}
