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
import { Asset } from '../../models/Asset.model';

@Component({
  selector: 'neocom-ship',
  templateUrl: './ship.component.html',
  styleUrls: ['./ship.component.css']
})
export class ShipComponent implements OnInit {
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: Asset;

  constructor(private appModelStore: AppModelStoreService) {
    //  super();
  }

  ngOnInit() {
  }
  /**
  If the Panel has some specific attributes they should be tested for display. The current actions are the Menu and the Expand Arrow.
  */
  public hasMenu(): boolean {
    return false;
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
  public totalValueIsk(): number {
    let value = this.node.item.baseprice * this.node.quantity;
    return value;
  }
  public totalVolume(): number {
    let value = 0;
    //  let value = this.node.item.volume * this.node.quantity;
    return value;
  }
}
