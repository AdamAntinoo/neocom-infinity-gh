import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { PilotManagerComponent } from '../pilot-manager/pilot-manager.component';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';
import { Manager } from '../../models/Manager.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';

@Component({
  selector: 'neocom-planetary-manager',
  templateUrl: './planetary-manager.component.html',
  styleUrls: ['./planetary-manager.component.css']
})
export class PlanetaryManagerComponent implements OnInit {
  @Input() pilot: NeoComCharacter;
  @Input() manager: Manager;

  constructor(private appModelStore: AppModelStoreService) {
    //  super();
  }

  ngOnInit() {
  }
  public getLoginId(): string {
    return this.appModelStore.accessLogin().getLoginId();
  }
}
