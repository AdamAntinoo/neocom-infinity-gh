import { Component, OnInit } from '@angular/core';
//import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { PageComponent } from '../../classes/PageComponent';
// import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- MODELS
import { NeoComNode } from '../../models/NeoComNode.model';

@Component({
  selector: 'neocom-region',
  templateUrl: './region.component.html',
  styleUrls: ['./region.component.css']
})
export class RegionComponent implements OnInit {
  @Input() viewer: PageComponent;
  @Input() node: NeoComNode;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
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
}
