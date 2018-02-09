import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- INTERFACES
import { PageComponent } from '../../../classes/PageComponent';
//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- COMPONENTS
// import { LoginPageComponent } from '../../../pages/login-page/login-page.component';
import { ComponentFactoryComponent } from '../../factory/component-factory/component-factory.component';
//--- MODELS
import { Login } from '../../../models/Login.model';
import { NeoComNode } from '../../../models/NeoComNode.model';

@Component({
  selector: 'neocom-credential',
  templateUrl: './credential.component.html',
  styleUrls: ['./credential.component.css']
})
export class CredentialComponent implements OnInit {
  @Input() viewer: ComponentFactoryComponent;
  @Input() node: NeoComNode;
  @Input() variant: string = "ROW";

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
  public hasMenu(): boolean {
    return false;
  }
  public isExpandable(): boolean {
    return false;
  }
  public mouseEnter(target: NeoComNode) {
    this.viewer.enterSelected(target);
  }
  public getRenderVariant(): string {
    return this.variant;
  }
}
