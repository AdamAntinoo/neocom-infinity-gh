export class ServerInfo {
    protected jsonClass: string;
    private name: string;
    private players: number = 0;
    private startTime: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
        this.jsonClass = "ServerInfo";
    }
    public getServerName(): string{
        return this.name;
    }
    public setServerName(name: string): ServerInfo{
        this.name = name;
        return this;
    }
    public getPlayersCount(): number {
        return this.players;
    }
    public getStartTime(): string {
        return "2019-10-15T11:06:17Z";
    }
}
