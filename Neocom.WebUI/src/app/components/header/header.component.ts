import { Component, OnInit } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';

@Component({
  selector: 'neocom-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private appModelStore: AppModelStoreService) { }

  ngOnInit() { }

  public getAppName() {
    return this.appModelStore.getApplicationName();
  }

  public getVersion() {
    return this.appModelStore.getApplicationVersion();
  }
}
