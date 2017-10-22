/**
The separator will render some kind of artefact to signal boundaries between node elements. There are some variations in color and in shape that are controlled by an input parameter that contains the Enumerated value of the variation to use.
*/
//--- CORE
import { Component, OnInit } from '@angular/core';
//import { Router, ActivatedRoute, ParamMap } from '@angular/router';
//import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
//import { PageComponent } from '../../classes/PageComponent';
//--- COMPONENTS
//import { RenderComponent } from '../render/render.component';
import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- INTERFACES
import { ESeparator } from '../../classes/ESeparator.enumerated';
//--- MODELS
//import { Render } from '../../models/Render.model';
//import { Location } from '../../models/Location.model';

@Component({
  selector: 'neocom-separator',
  templateUrl: './separator.component.html',
  styleUrls: ['./separator.component.css']
})
export class SeparatorComponent implements OnInit {
  //  @Input() variation: ESeparator=ESeparator.ORANGE;
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: any;

  constructor(private appModelStore: AppModelStoreService) {
    //    super();
  }
  ngOnInit() { }

  public hasMenu(): boolean {
    return false;
  }
  public isExpandable(): boolean {
    return false;
  }
  public getVariation(): ESeparator {
    return this.node.variation;
  }
  public isRed(): boolean {
    if (this.node.variation == ESeparator.RED) return true;
    else return false;
  }
  public isOrange(): boolean {
    if (this.node.variation == ESeparator.ORANGE) return true;
    else return false;
  }
  public isGreen(): boolean {
    if (this.node.variation == ESeparator.GREEN) return true;
    else return false;
  }
  public isBlue(): boolean {
    if (this.node.variation == ESeparator.BLUE) return true;
    else return false;
  }
  public isEmpty(): boolean {
    if (this.node.variation == ESeparator.EMPTY) return true;
    else return false;
  }
  public isSpinner(): boolean {
    if (this.node.variation == ESeparator.SPINNER) return true;
    else return false;
  }
}
