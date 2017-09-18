import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- MODELS
import { Render } from '../../models/Render.model';

@Component({
  selector: 'neocom-location',
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css']
})
export class LocationComponent implements OnInit {
  @Input() node: Render;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
  public toggleExpanded() {
    this.node.toggleExpanded();
  }

}
