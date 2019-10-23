// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { ServerStatus } from '@app/domain/ServerStatus.domain';
import { BackendService } from '@app/services/backend.service';
import { environment } from '@env/environment';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';

@Component({
    selector: 'server-info-panel',
    templateUrl: './server-info-panel.component.html',
    styleUrls: ['./server-info-panel.component.scss']
})
export class ServerInfoPanelComponent implements OnInit {
    private serverInfoSubscription: Subscription;
    private serverInfo: ServerStatus;

    constructor(protected backendService: BackendService) { }

    ngOnInit() {
        this.serverInfoSubscription = this.backendService.apiGetServerInfo_v1(
            new ResponseTransformer().setDescription('Do response transformation to "ServerStatus".')
                .setTransformation((data: any): ServerStatus => {
                    return new ServerStatus(data);
                }))
            .subscribe(info => {
                this.serverInfo = info;
            });
    }

    public getServerName(): string {
        if (null != this.serverInfo) return this.serverInfo.getServerName();
        else return '-PENDING-UPDATE-';
    }
    public getServerStatus(): string {
        if (null != this.serverInfo) return "online".toUpperCase();
        else return "WAITING".toUpperCase();
    }
    public getServerCapsuleers(): number {
        if (null != this.serverInfo) return this.serverInfo.getPlayersCount();
        else return -1;
    }
    public getLastStartTime(): string {
        if (null != this.serverInfo) return this.serverInfo.getStartTime();
        else return new Date().toDateString();
    }
}
