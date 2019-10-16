export class ServerInfoResponse{
    public players: number = -1;
    public server_version: string;
    public start_time: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}
