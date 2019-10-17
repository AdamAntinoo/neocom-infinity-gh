// - CORE
import { NeoComResponse } from './NeoComResponse';
import { Credential } from '../Credential.domain';

export class ValidateAuthorizationTokenResponse extends NeoComResponse {
    private jwtToken: string;
    private credential: Credential;

    constructor(values: Object = {}) {
        super(values);
        Object.assign(this, values);
        // Transform dependency objects
        if (null != this.credential) {
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
