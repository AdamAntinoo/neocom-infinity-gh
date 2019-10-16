import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'server-info-panel',
    templateUrl: './server-info-panel.component.html',
    styleUrls: ['./server-info-panel.component.scss']
})
export class ServerInfoPanelComponent implements OnInit {

    constructor() { }

    ngOnInit() {
    }

    public getServerName(): string {
        return "Tranquility";
    }
    public getServerStatus(): string {
        return "online".toUpperCase();
    }
    public getServerCapsuleers(): number {
        return 123456;
    }
    public getLastStartTime(): string {
        return "2019-10-15T11:06:17Z";
    }
}
