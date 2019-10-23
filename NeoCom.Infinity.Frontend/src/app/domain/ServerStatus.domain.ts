// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class ServerStatus extends NeoCom {
    private server: string;
    private players: number = 0;
    private server_version: string;
    private start_time: string;

    constructor(values: Object = {}) {
        super();
        Object.assign(this, values);
        this.jsonClass = "ServerStatus";
    }
    public getServerName(): string {
        return this.server;
    }
    public getPlayersCount(): number {
        return this.players;
    }
    public getStartTime(): string {
        return this.server;
    }
}
