/**
The separator will render some kind of artefact to signal boundaries between node elements. There are some variations in color and in shape that are controlled by an input parameter that contains the Enumerated value of the variation to use.
*/
//--- CORE
import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- COMPONENTS
import { IViewer } from '../../../interfaces/iviewer.interface';
// import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- INTERFACES
import { ESeparator } from '../../../classes/ESeparator.enumerated';

@Component({
  selector: 'neocom-separator',
  templateUrl: './separator.component.html',
  styleUrls: ['./separator.component.scss']
})
export class SeparatorComponent implements OnInit {
  //  @Input() variation: ESeparator=ESeparator.ORANGE;
  @Input() viewer: IViewer;
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
  public isYellow(): boolean {
    if (this.node.variation == ESeparator.YELLOW) return true;
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
  public isWhite(): boolean {
    if (this.node.variation == ESeparator.WHITE) return true;
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
