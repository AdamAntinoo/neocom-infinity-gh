// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { OnDestroy } from '@angular/core';
import { environment } from '@env/environment';
import { Subscription } from 'rxjs';
// - SERVICES
import { BackendService } from '@app/services/backend.service';
import { AppStoreService } from '@app/services/appstore.service';
// - DOMAIN
import { Corporation } from '@app/domain/Corporation.domain';
import { Alliance } from '@app/domain/Alliance.domain';
import { Pilot } from '@app/domain/Pilot.domain';

@Component({
   selector: 'corporation-public-data-panel',
   templateUrl: './corporation-public-data-panel.component.html',
   styleUrls: ['./corporation-public-data-panel.component.scss']
})
export class CorporationPublicDataPanelComponent implements OnInit, OnDestroy {
   private corporationSubscription: Subscription;
   public corporation: Corporation; // The authorized corporation public data.
   public ceo: Pilot; // The Corporation CEO public pilot data.
   public alliance: Alliance; // A link to the Alliance record. Maybe null if the corporation is not associated.

   constructor(
      protected appStoreService: AppStoreService,
      protected backendService: BackendService) { }

   ngOnInit() {
      this.corporationSubscription = this.appStoreService.accessCorporation()
         .subscribe((corporation: Corporation) => {
            console.log('-[CorporationPublicDataPanelComponent]> processing response.');
            console.log('-[CorporationPublicDataPanelComponent]> Corporation: '+JSON.stringify(corporation));
            this.corporation = corporation;
            // this.alliance = this.corporation.alliance;
         });
   }
   ngOnDestroy() {
      if (null != this.corporationSubscription) this.corporationSubscription.unsubscribe();
   }
   public getCorporationId(): number {
      if (!this.isEmpty(this.corporation)) return this.corporation.corporationId;
      else return 0;
   }
   public getCorporationName(): string {
      if (!this.isEmpty(this.corporation)) return this.corporation.name;
      else return '-';
   }
   public getCorporationIcon(): string {
      if (!this.isEmpty(this.corporation)) return this.corporation.getIconUrl();
      else return environment.DEFAULT_AVATAR_PLACEHOLDER;
   }
   public getAlliance(): Alliance {
      return this.alliance;
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
