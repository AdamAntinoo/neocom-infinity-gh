import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { PilotManagerComponent } from '../pilot-manager/pilot-manager.component';
//--- CLASSES
import { PageComponent } from '../../classes/PageComponent';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';
import { Manager } from '../../models/Manager.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';

@Component({
  selector: 'neocom-assets-manager',
  templateUrl: './assets-manager.component.html',
  styleUrls: ['./assets-manager.component.css']
})
export class AssetsManagerComponent extends PageComponent implements OnInit {
  @Input() pilot: NeoComCharacter;
  @Input() manager: Manager;

  constructor(private appModelStore: AppModelStoreService) {
    super();
    this.setExpandable(true);
  }

  ngOnInit() {
  }
  public getLoginId(): string {
    return this.appModelStore.accessLogin().getLoginId();
  }
}
