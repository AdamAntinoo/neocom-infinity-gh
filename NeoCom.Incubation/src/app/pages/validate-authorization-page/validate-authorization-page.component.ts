//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
//--- ENVIRONMENT
import { environment } from '../../../environments/environment';
//--- ROUTER
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { ParamMap } from '@angular/router';
import { NavigationCancel } from '@angular/router';
import 'rxjs/add/operator/switchMap';
//--- NOTIFICATIONS
// import { ToastsManager } from 'ng2-toastr/ng2-toastr';
//--- SERVICES
import { AuthConfig } from 'angular-oauth2-oidc';
import { OAuthService } from 'angular-oauth2-oidc';
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
// import { EVariant } from '../../classes/EVariant.enumerated';
// import { IDetailedEnabledPage } from '../../classes/IDetailedEnabledPage.interface';
// import { INeoComNode } from '../../classes/INeoComNode.interface';
//--- COMPONENTS
// import { BasePageComponent } from '../../components/core/base-page/base-page.component';
//--- MODELS
// import { NeoComNode } from '../../models/NeoComNode.model';
// import { Credential } from '../../models/Credential.model';

@Component({
  selector: 'neocom-validate-authorization-page',
  templateUrl: './validate-authorization-page.component.html',
  styleUrls: ['./validate-authorization-page.component.scss']
})
export class ValidateAuthorizationPageComponent implements OnInit {
  public code: string;

  constructor(protected appModelStore: AppModelStoreService
    , protected router: Router
    , protected route: ActivatedRoute
    , private oauthService: OAuthService) { }

  ngOnInit() {
    console.log(">>[ValidateAuthorizationPageComponent.ngOnInit]");
    this.route.queryParams
      .subscribe(params => {
        this.code = params['code'];
        // Call the backend to exchange the token and store the resulting credential.
        this.appModelStore.backendExchangeAuthorization(this.code)
          .subscribe(result => {
            console.log("--[AppModelStoreService.backendExchangeAuthorization]> Processing response." + JSON.stringify(result));
            // Redirect to the credential list to reload again the list of credentials.
            this.router.navigate(['dashboard']);
          })
      });
  }
}
