// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { ServerInfo } from '@app/domain/ServerInfo.domain';
import { BackendService } from '@app/services/backend.service';
import { environment } from '@env/environment';

@Component({
   selector: 'server-info-panel',
   templateUrl: './server-info-panel.component.html',
   styleUrls: ['./server-info-panel.component.scss']
})
export class ServerInfoPanelComponent implements OnInit {
   private serverInfoSubscription: Subscription;
   private serverInfo: ServerInfo;

   constructor(protected backendService: BackendService) { }

   ngOnInit() {
      this.serverInfoSubscription = this.backendService.apiGetServerInfo_v1()
         .subscribe(serverDataResponse => {
            this.serverInfo = new ServerInfo({
               players: serverDataResponse.players,
               startTime: serverDataResponse.start_time
            })
            this.serverInfo.setServerName(environment.ESIDataSource);
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
