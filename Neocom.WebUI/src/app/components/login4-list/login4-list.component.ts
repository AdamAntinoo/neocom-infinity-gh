import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- MODELS
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-login4-list',
  templateUrl: './login4-list.component.html',
  styleUrls: ['./login4-list.component.css']
})
export class Login4ListComponent implements OnInit {
  @Input() node: Login;

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() {
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
}
