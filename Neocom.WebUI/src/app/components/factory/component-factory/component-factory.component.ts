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
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- INTERFACES
import { PageComponent } from '../../../classes/PageComponent';
import { EVariant } from '../../../classes/EVariant.enumerated';
import { DataSource } from '../../../models/DataSource.model';
import { NeoComError } from '../../../classes/NeoComError';
//--- MODELS
import { NeoComNode } from '../../../models/NeoComNode.model';

@Component({
	selector: 'neocom-component-factory',
	templateUrl: './component-factory.component.html',
	styleUrls: ['./component-factory.component.css']
})
export class ComponentFactoryComponent extends PageComponent implements OnInit {
	// This is the connection with the Page. This object will be able to genrate the list of nodes to be rendered.
	@Input() dataSource: DataSource;

	constructor() {
		super();
	}

	ngOnInit() {
	}
	public getViewer(): PageComponent {
		return this;
	}
	/**
	This is the connection method that will call the DataSource to get the list of nodes to render. This replicates the collaborate2View and getBodyParts functioanlities of the Android platform and will start the process to render the ui.
	*/
	public getBodyParts(): NeoComNode[] {
		if (null != this.dataSource) return this.dataSource.getBodyParts();
		else {
			// Create a new list and add an exception node to report the message to the user.
			let results = [];
			results.push(new NeoComError({ "message": "WR [ComponentFactoryComponent]> DataSource is null. There is nothing to render." }));
		}
	}
}
