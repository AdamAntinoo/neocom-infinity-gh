// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { environment } from '@env/environment';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
import { AppStoreService } from '@app/services/appstore.service';
// - DOMAIN
import { Corporation } from '@app/domain/Corporation.domain';
import { Alliance } from '@app/domain/Alliance.domain';
import { Pilot } from '@app/domain/Pilot.domain';
import { CorporationDataResponse } from '@app/domain/dto/CorporationDataResponse.dto';

@Component({
   selector: 'pilot-public-data-panel',
   templateUrl: './pilot-public-data-panel.component.html',
   styleUrls: ['./pilot-public-data-panel.component.scss']
})
export class PilotPublicDataPanelComponent implements OnInit, OnDestroy {
   private pilotSubscription: Subscription;
   public pilot: Pilot;

   constructor(protected appStoreService: AppStoreService) { }

   ngOnInit() {
      this.pilotSubscription = this.appStoreService.accessPilot()
         .subscribe((pilot: Pilot) => {
            this.pilot = pilot;
         });
   }
   ngOnDestroy() {
      if (null != this.pilotSubscription) this.pilotSubscription.unsubscribe();
   }
   public getPilotIcon(): string {
      if (null != this.pilot) return this.pilot.url4Icon;
      else return environment.DEFAULT_AVATAR_PLACEHOLDER;
   }
}
