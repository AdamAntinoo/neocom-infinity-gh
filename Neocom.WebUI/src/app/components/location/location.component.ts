import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { PageComponent } from '../../classes/PageComponent';
//--- COMPONENTS
//import { RenderComponent } from '../render/render.component';
import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Location } from '../../models/Location.model';

@Component({
  selector: 'neocom-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: Location;

  constructor(private appModelStore: AppModelStoreService) {
    //    super();
  }

  ngOnInit() {
  }
  public toggleExpanded() {
    this.node.toggleExpanded();
    this.viewer.refreshViewPort();
  }
  /**
  If the Panel has some specific attributes they should be tested for display. The current actions are the Menu and the Expand Arrow.
  */
  public hasMenu(): boolean {
    return true;
  }
  public getLoginId(): string {
    return this.appModelStore.accessLogin().getLoginId();
  }
  public getCharacterId() {
    return this.viewer.pilot.getId()
  }
  public getLocationId() {
    return this.node.getLocationId();
  }
}
