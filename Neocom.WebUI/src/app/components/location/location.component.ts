import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { EVariant } from '../../classes/EVariant.enumerated';
//--- COMPONENTS
import { PageComponent } from '../../classes/PageComponent';
//import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- MODELS
import { Location } from '../../models/Location.model';

@Component({
  selector: 'neocom-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  @Input() viewer: PageComponent;
  @Input() node: Location;

  constructor(private appModelStore: AppModelStoreService) {
    //    super();
  }

  ngOnInit() {
  }

  /**
  If the Panel has some specific attributes they should be tested for display. The current actions are the Menu and the Expand Arrow.
  Locations have menu onlt on Planetary environments. Remove the shown when we are in other Managers.
  */
  public hasMenu(): boolean {
    // Check the Page variant to see if we show the menu or not.
    if (this.viewer.getVariant() == EVariant.PLANETARYMANAGER)
      return true;
    else return false;
  }
  public isExpandable(): boolean {
    return true;
  }
  /**
  Toggle the expand collapse status. This changes the expanded attribute and also ndicates other visual elements to change (like the arrow or the shade of the background).
  The second action is to generate again the view llist with a new call to the page component 'refreshViewPort'.
  */
  public clickArrow() {
    this.node.toggleExpanded();
    this.viewer.refreshViewPort();
  }
  public getIdentifier(): number {
    return Math.max(Math.max(Math.max(this.node.stationID, this.node.systemID), this.node.constellationID), this.node.regionID);
  }
  // public toggleExpanded() {
  //   this.node.toggleExpanded();
  //   this.viewer.refreshViewPort();
  // }
  public getLoginId(): string {
    return this.appModelStore.accessLogin().getLoginId();
  }
  // public getCharacterId() {
  //   return this.viewer.pilot.getId()
  // }
  public getLocationId() {
    return this.node.getLocationId();
  }
}
