import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { LoginPageComponent } from '../../pages/login-page/login-page.component';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-pilot4-roaster',
  templateUrl: './pilot4-roaster.component.html',
  styleUrls: ['./pilot4-roaster.component.css']
})
export class Pilot4RoasterComponent implements OnInit {
  @Input() viewer: LoginPageComponent;
  @Input() node: Pilot;
  public expanded: boolean = false;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
  public getCharacterClass(): string {
    if (this.node.corporation == true) return "CORPORATION";
    else return "PILOT";
  }
  public onClickArrow(): void {
    this.expanded = !this.expanded;
  }
  public getLoginId(): string {
    return this.appModelStore.accessLogin().getLoginId();
  }
}
