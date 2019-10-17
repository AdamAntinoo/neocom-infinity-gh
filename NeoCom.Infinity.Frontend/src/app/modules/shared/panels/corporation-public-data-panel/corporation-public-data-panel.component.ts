// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { Input } from '@angular/core';
import { environment } from '@env/environment';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
// - DOMAIN
import { Corporation } from '@app/domain/Corporation.domain';
import { Alliance } from '@app/domain/Alliance.domain';
import { Pilot } from '@app/domain/Pilot.domain';
import { CorporationDataResponse } from '@app/domain/dto/CorporationDataResponse.dto';
import { AppStoreService } from '@app/services/appstore.service';

@Component({
   selector: 'corporation-public-data-panel',
   templateUrl: './corporation-public-data-panel.component.html',
   styleUrls: ['./corporation-public-data-panel.component.scss']
})
export class CorporationPublicDataPanelComponent implements OnInit, OnDestroy {
   private corporationSubscription: Subscription;
   private corporation: Corporation; // The authorized corporation public data.
   private ceo: Pilot; // The Corporation CEO public pilot data.
   private alliance: Alliance; // A link to the Alliance record. Maybe null if the corporation is not associated.

   constructor(
      protected appStoreService: AppStoreService,
      protected backendService: BackendService) { }

   ngOnInit() {
      const corporationId = this.appStoreService.getCorporationIdentifier();
      this.corporationSubscription = this.backendService.apiGetCorporationPublicData_v1(corporationId)
         .subscribe((corporationResponse: CorporationDataResponse) => {
            this.corporation = corporationResponse.corporation;
            this.ceo = corporationResponse.ceoPilotData;
            this.alliance = this.corporation.alliance;
         });
   }
   ngOnDestroy() {
      if (null != this.corporationSubscription) this.corporationSubscription.unsubscribe();
   }
   public getAlliance(): Alliance {
      return this.alliance;
   }
   public getCorporationIcon(): string {
      if (!this.isEmpty(this.corporation)) return this.corporation.getIconUrl();
      else return environment.DEFAULT_AVATAR_PLACEHOLDER;
   }
   public getAllianceIcon(): string {
      if (!this.isEmpty(this.alliance)) return this.alliance.getIconUrl();
      else return environment.DEFAULT_AVATAR_PLACEHOLDER;
   }
   protected isEmpty(target: any): boolean {
      if (null == target) return true;
      if (Object.keys(target).length > 0) return false;
      return true;
   }
}
