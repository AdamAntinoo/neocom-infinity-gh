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
import { NeoComNode } from '../../models/NeoComNode.model';

@Component({
  selector: 'neocom-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css']
})
export class LoginPageComponent extends PageComponent implements OnInit {
  public loginViewList: NeoComNode[] = [];
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
    this.refreshViewPort();
    console.log("<<[LoginPageComponent.ngOnInit]");
  }
  /**
  Indicates the viewer container that the model states have changed and that a new Collaborate2View should be executed to generate the new view list.
  */
  public refreshViewPort(): void {
    console.log(">>[LoginPageComponent.refreshViewPort]");
    this.downloading = true;
    // Call the service to get the list of Logins.
    this.appModelStore.accessLoginList()
      .subscribe(result => {
        console.log("--[LoginPageComponent.ngOnInit.accessLoginList]>Loginlist.length: " + result.length);
        this.loginViewList = [];
        // Sort the list of Logins before processing their collaborations.
        let sortedLogins = this.sortLogins(result);
        // Loop over all the returned items.
        for (let node of result) {
          // Add to the result only the Logins with at least one character.
          if (node.getKeyCount() > 0) {
            let theList = node.collaborate2View(this.appModelStore, this.getVariant());
            this.loginViewList = this.loginViewList.concat(theList);
          }
        }
        this.downloading = false;
      });
    console.log("<<[LoginPageComponent.refreshViewPort]");
  }
  public getViewer(): LoginPageComponent {
    return this;
  }

  private sortLogins(nodeList: Login[]): Login[] {
    let sortedContents: Login[] = nodeList.sort((n1, n2) => {
      if (n1.getLoginId() > n2.getLoginId()) {
        return 1;
      }
      if (n1.getLoginId() < n2.getLoginId()) {
        return -1;
      }
      return 0;
    });
    return sortedContents;
  }
}
