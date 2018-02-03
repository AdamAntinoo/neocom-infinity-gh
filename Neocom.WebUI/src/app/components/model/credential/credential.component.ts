import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- INTERFACES
import { PageComponent } from '../../../classes/PageComponent';
//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- COMPONENTS
import { LoginPageComponent } from '../../../pages/login-page/login-page.component';
//--- MODELS
import { Login } from '../../../models/Login.model';

@Component({
	selector: 'neocom-credential',
	templateUrl: './credential.component.html',
	styleUrls: ['./credential.component.css']
})
export class CredentialComponent implements OnInit {
	@Input() viewer: PageComponent;
	@Input() node: Login;

	constructor(private appModelStore: AppModelStoreService) { }

	ngOnInit() {
	}
	public hasMenu(): boolean {
		return false;
	}
	public isExpandable(): boolean {
		return false;
	}
}
