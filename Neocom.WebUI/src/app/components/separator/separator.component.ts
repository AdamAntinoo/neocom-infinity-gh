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
  selector: 'neocom-separator',
  templateUrl: './separator.component.html',
  styleUrls: ['./separator.component.css']
})
export class SeparatorComponent implements OnInit {
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: any;

  constructor(private appModelStore: AppModelStoreService) {
    //    super();
  }

  ngOnInit() {
  }
  public hasMenu(): boolean {
    return false;
  }
  public isExpandable(): boolean {
    return false;
  }
}
