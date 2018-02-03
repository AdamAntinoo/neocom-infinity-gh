//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms
//               , the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code maid in typescript within the Angular
//               framework.
//--- CORE
import { Component, OnInit } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../../classes/PageComponent';
import { EVariant } from '../../../classes/EVariant.enumerated';

@Component({
	selector: 'neocom-base-page',
	templateUrl: './base-page.component.html',
	styleUrls: ['./base-page.component.css']
})
export class BasePageComponent extends PageComponent implements OnInit {
	//  private _variant: EVariant = EVariant.DEFAULT;

	constructor(private appModelStore: AppModelStoreService) {
		super();
	}

	ngOnInit() {
	}

}
