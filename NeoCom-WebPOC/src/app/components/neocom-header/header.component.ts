import { Component, OnInit } from '@angular/core';

//--- SERVICES
//import { AppModelStoreService } from '../../services/app-model-store.service';

@Component({
  selector: 'neocom-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor() { }

  ngOnInit() { }

  public getAppName() {
    return "NeoCom POC";
  }

  public getVersion() {
    return "v0.0.1";
  }
}
