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
import { Resource } from '../../models/Resource.model';

@Component({
  selector: 'neocom-resource',
  templateUrl: './resource.component.html',
  styleUrls: ['./resource.component.css']
})
export class ResourceComponent implements OnInit {
  //  @Input() viewer: PlanetaryManagerPageComponent;
  @Input() node: Resource;
  public value: number = 1234567;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
}
