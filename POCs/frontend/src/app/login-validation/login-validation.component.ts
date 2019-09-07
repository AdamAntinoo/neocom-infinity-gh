import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BackendService } from '../services/backend.service';
import { AppStoreService } from '../services/app.store.service';
//--- SERVICES
// import { AppModelStoreService } from '../../services/app-model-store.service';
// import { AppStoreService } from 'app/services/app-store.service';
// import { BackendService } from 'app/services/backend.service';

@Component({
  selector: 'app-login-validation',
  templateUrl: './login-validation.component.html',
  styleUrls: ['./login-validation.component.scss']
})
export class LoginValidationComponent implements OnInit {
  param1: string;
  param2: string;
  constructor(protected appModelStore: AppStoreService,
    protected backendService: BackendService,
    protected route: ActivatedRoute) {
    console.log('>LoginValidation');
    this.route.queryParams.subscribe(params => {
      this.param1 = params['code'];
      this.param2 = params['state'];
    });
    console.log('Parameters: ' + 'code=' + this.param1);
    console.log('Parameters: ' + 'state=' + this.param2);
  }

  ngOnInit() {
    // Read the request parameters and check the caller is verified.
    console.log(">>[ValidateAuthorizationPageComponent.ngOnInit]");
    // Read the code to be interchanged from the URL query string.
    this.route.queryParams
      .subscribe(params => {
        let code = params['code'];
        // If the code is valid then generate the basic authorization and transfer the encoded data to the backend.
        if (null != code) {
          this.backendService.backendExchangeAuthorization(code);
        } else {
          // this.exception = true;
        }
      });
  }

}
