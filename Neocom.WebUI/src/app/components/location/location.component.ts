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

@Component({
  selector: 'neocom-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent extends RenderComponent implements OnInit {
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: Render;

  constructor(private appModelStore: AppModelStoreService) {
    super();
  }

  ngOnInit() {
  }
  public toggleExpanded() {
    this.node.toggleExpanded();
    this.viewer.refreshViewPort();
  }

}
