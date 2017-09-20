// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - Page Component
//
/** Defines the Login page. At this page we pretend to show the list of Logins
available on the NeoCom database. Then the User may be able to select one and
then introduce the corresponding password. He/she can also add new Logins or edit
the ones available by adding Keys to them. Finally the interface may allow to
create and delete Logins.
*/

import { Component, OnInit } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- MODELS
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent implements OnInit {
  public loginViewList: Login[] = [];
  public downloading: boolean = true;

  constructor(private appModelStore: AppModelStoreService) {
    this.downloading = true;
  }

  /** During the initialization we should use an special Service. Services are always
   available from application start so they are the natural place to store
   application global data. So we can use the Service to retrieve from the database
   that list or to return the already cached list if that operation was already done.
   */
  ngOnInit() {
    console.log(">>[LoginPageComponent.ngOnInit]");
    // Call the service to get the list of Logins.
    this.appModelStore.accessLoginList()
      .subscribe(result => {
        console.log("--[LoginPageComponent.ngOnInit.accessLoginList]>Loginlist: " + JSON.stringify(result));
        this.loginViewList = result;
        this.downloading = false;
      });
    console.log("<<[LoginPageComponent.ngOnInit]");
  }
}
