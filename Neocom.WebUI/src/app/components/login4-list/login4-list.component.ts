import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- COMPONENTS
import { LoginPageComponent } from '../../pages/login-page/login-page.component';
//--- MODELS
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-login4-list',
  templateUrl: './login4-list.component.html',
  styleUrls: ['./login4-list.component.css']
})
export class Login4ListComponent implements OnInit {
  @Input() viewer: LoginPageComponent;
  @Input() node: Login;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
  }
  /**
  Toggle the expand collapse status. This changes the expanded attribute and also ndicates other visual elements to change (like the arrow or the shade of the background).
  The second action is to generate again the view llist with a new call to the page component 'refreshViewPort'.
  */
  public toggleExpanded() {
    this.node.toggleExpanded();
    this.viewer.refreshViewPort();
  }
  /**
  Broadcast to the node the request but adding a reference to the Service to be able to complete the operation if the data is not already downloaded.
  */
  public getKeyCount(): number {
    return 3;
    //    return this.node.getKeyCountObsrver(this.appModelStore);
  }
  public isExpandable(): boolean {
    return true;
  }
  public getLoginId(): string {
    return this.appModelStore.accessLogin().getLoginId();
  }
}
