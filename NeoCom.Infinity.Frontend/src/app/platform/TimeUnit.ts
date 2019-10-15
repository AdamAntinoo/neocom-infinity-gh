export class TimeUnit {
    private multiplier: number = 1;
    static HOUR: number=3600;

    constructor(secondsMultiplier: number) {
        this.multiplier = secondsMultiplier;
    }
    public toMillis(times: number): number {
        return times * this.multiplier * 1000;
    }
}
