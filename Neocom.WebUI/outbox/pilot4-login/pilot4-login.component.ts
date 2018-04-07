import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { LoginPageComponent } from '../../pages/login-page/login-page.component';
//--- MODELS
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-pilot4-login',
  templateUrl: './pilot4-login.component.html',
  styleUrls: ['./pilot4-login.component.css']
})
export class Pilot4LoginComponent implements OnInit {
  @Input() viewer: LoginPageComponent;
  @Input() node: NeoComCharacter;
  public expanded: boolean = false;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
  public hasMenu(): boolean {
    return false;
  }
  public isExpandable(): boolean {
    return false;
  }
  public onClickArrow(): void {
    this.expanded = !this.expanded;
  }
  public getCharacterClass(): string {
    if (this.node.corporation == true) return "CORPORATION";
    else return "PILOT";
  }
  public getLoginId(): string {
    return this.node.getLoginRefId();
  }
}