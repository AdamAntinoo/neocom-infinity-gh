import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { PageComponent } from '../../classes/PageComponent';
import { PlanetaryManagerPageComponent } from '../../pages/planetary-manager-page/planetary-manager-page.component';
//--- MODELS
import { Render } from '../../models/Render.model';
import { ProcessingAction } from '../../models/ProcessingAction.model';

@Component({
  selector: 'neocom-processing-action',
  templateUrl: './processing-action.component.html',
  styleUrls: ['./processing-action.component.css']
})
export class ProcessingActionComponent implements OnInit {
  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: ProcessingAction;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
  public toggleExpanded() {
    this.node.toggleExpanded();
  }
  public getInputs() {
    if (null != this.node) return this.node.inputs;
    else return [];
  }
  public getOutputs() {
    if (null != this.node) return this.node.outputs;
    else return [];
  }
}
