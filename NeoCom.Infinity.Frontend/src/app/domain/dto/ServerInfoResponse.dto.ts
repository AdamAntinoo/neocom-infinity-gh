import { NeoComResponse } from './NeoComResponse';

export class ServerInfoResponse extends NeoComResponse{
    public players: number = -1;
    public server_version: string;
    public start_time: string;

    constructor(values: Object = {}) {
       super(values);
        Object.assign(this, values);
    }
}
