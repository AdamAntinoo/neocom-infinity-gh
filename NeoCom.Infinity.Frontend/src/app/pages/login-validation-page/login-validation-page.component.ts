// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { environment } from '@env/environment';
import { Subscription } from 'rxjs';
// - SERVICES
import { AppStoreService } from '@app/services/appstore.service';
import { BackendService } from '@app/services/backend.service';
import { NeoComException } from '@app/platform/NeoComException';

@Component({
	selector: 'app-login-validation-page',
	templateUrl: './login-validation-page.component.html',
	styleUrls: ['./login-validation-page.component.scss']
})
export class LoginValidationPageComponent implements OnInit {
	public validationException: any;
	private paramCode: string;
	private paramState: string;
	private validateAuthorizationTokenSubscription: Subscription;

	constructor(protected appModelStore: AppStoreService,
		protected backendService: BackendService,
		protected route: ActivatedRoute) {
		console.log('>LoginValidationPageComponent.<constructor>');
		this.route.queryParams.subscribe(params => {
			this.paramCode = params['code'];
			this.paramState = params['state'];
		});
		console.log('-LoginValidationPageComponent.<constructor> Query Parameter: ' + 'code=' + this.paramCode);
		console.log('-LoginValidationPageComponent.<constructor> Query Parameter: ' + 'state=' + this.paramState);
		console.log('<LoginValidationPageComponent.<constructor>');
	}

	ngOnInit() {
		if (this.parameterValidation()) {
			console.log('-LoginValidationPageComponent.<ngOnInit> Validation should be true');
			this.validateAuthorizationTokenSubscription = this.backendService.apiValidateAuthorizationToken_v1(
				this.paramCode, this.paramState)
				.subscribe(response => {
					console.log('-LoginValidationPageComponent.<ngOnInit>.apiValidateAuthorizationToken');
				});
		} else
			console.log('-LoginValidationPageComponent.<ngOnInit> Validation should be false');
	}
	public getAppName(): string {
		return this.backendService.getApplicationName();
	}
	public getAppVersion(): string {
		return this.backendService.getApplicationVersion();
	}

	// - F U N C T I O N A L I T I E S
    /**
     * Validates that the page has received the correct paramters. Checks the content for the 'state' and that the
     * 'code' exists before allowing the page to continue to the backend endpoint call.
     */
	private parameterValidation(): boolean {
		this.validateState(this.paramState);
		if (this.appModelStore.isEmptyString(this.paramCode))
			this.validationException = new NeoComException(
				{ code: 400, message: 'The request does not have a mandatory query parameter.' }
			);
		if (null == this.validationException) return true;
		else return false;
	}
	private validateState(state2Check: string): void {
		console.log('-LoginValidationPageComponent.validateState');
		if (state2Check === environment.VALID_STATE) return;
		this.validationException = new NeoComException(
			{ code: 400, message: 'The request state does not match. Caller not verified.' }
		);
	}
}
