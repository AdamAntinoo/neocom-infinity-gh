import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { PageComponent } from '../../classes/PageComponent';
//--- COMPONENTS
import { RenderComponent } from '../render/render.component';
import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Asset } from '../../models/Asset.model';

@Component({
  selector: 'neocom-asset',
  templateUrl: './asset.component.html',
  styleUrls: ['./asset.component.css']
})
export class AssetComponent implements OnInit {
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: Asset;

  constructor(private appModelStore: AppModelStoreService) {
    //  super();
  }

  ngOnInit() {
  }
  public totalValueIsk(): number {
    let value = this.node.item.baseprice * this.node.quantity;
    return value;
  }
  public totalVolume(): number {
    let value = this.node.item.volume * this.node.quantity;
    return value;
  }
}
