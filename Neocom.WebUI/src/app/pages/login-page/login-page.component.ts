// PROJECT:     NEOCOM.WEB (NEOC.W)
// AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
// COPYRIGHT:   (c) 2017 by Dimensinfin Industries, all rights reserved.
// ENVIRONMENT: Angular - Page Component

/** Defines the Login page. At this page we pretend to show the list of Logins available on the NeoCom database. Inisde the Login there are the associated keys and then those keys contain the list of Characters. So in a single plage we get 3 levels on the data hierarchy.
Then the User may be able to select one and then introduce the corresponding password. He/she can also add new Logins or edit the ones available by adding Keys to them. Finally the interface may allow to create and delete Logins.
*/

import { Component, OnInit } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../classes/PageComponent';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- MODELS
import { Login } from '../../models/Login.model';

@Component({
  selector: 'neocom-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent extends PageComponent implements OnInit {
  public loginViewList: any[] = [];
  public downloading: boolean = true;

  constructor(private appModelStore: AppModelStoreService) {
    super();
    this.setVariant(EVariant.LOGINLIST)
  }

  /** During the initialization we should use an special Service. Services are always
   available from application start so they are the natural place to store
   application global data. So we can use the Service to retrieve from the database
   that list or to return the already cached list if that operation was already done.
   */
  ngOnInit() {
    console.log(">>[LoginPageComponent.ngOnInit]");
    this.downloading = true;
    // Call the service to get the list of Logins.
    this.appModelStore.accessLoginList()
      .subscribe(result => {
        console.log("--[LoginPageComponent.ngOnInit.accessLoginList]>Loginlist.length: " + result.length);
        this.loginViewList = [];
        // Loop over all the returned items.
        for (let node of result) {
          let thelist = node.collaborate2View(this.getVariant());
          this.loginViewList = this.loginViewList.concat(thelist);
        }
        //    this.loginViewList = result;
        this.downloading = false;
      });
    console.log("<<[LoginPageComponent.ngOnInit]");
  }
  /**
  Indicates the viewer container that the model states have changed and that a new Collaborate2View should be executed to generate the new view list.
  */
  public refreshViewPort(): void {
    this.downloading = true;
    // Call the service to get the list of Logins. Then create the collaboration list formeach of them.
    this.appModelStore.accessLoginList()
      .subscribe(result => {
        console.log("--[LoginPageComponent.ngOnInit.accessLoginList]>Loginlist.length: " + result.length);
        this.loginViewList = [];
        // Loop over all the returned items.
        for (let node of result) {
          let thelist = node.collaborate2View(this.getVariant());
          this.loginViewList = this.loginViewList.concat(thelist);
        }
        //    this.loginViewList = result;
        this.downloading = false;
      });
  }
  public getViewer(): LoginPageComponent {
    return this;
  }
}
