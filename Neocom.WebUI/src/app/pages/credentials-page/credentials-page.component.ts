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
import { AppModelStoreService } from '../../services/app-model-store.service';
//--- INTERFACES
import { BasePageComponent } from '../../components/core/base-page/base-page.component';
import { EVariant } from '../../classes/EVariant.enumerated';
//--- MODELS
//import { Login } from '../../models/Login.model';
import { NeoComNode } from '../../models/NeoComNode.model';

@Component({
	selector: 'neocom-credentials-page',
	templateUrl: './credentials-page.component.html',
	styleUrls: ['./credentials-page.component.css']
})
export class CredentialsPageComponent extends BasePageComponent implements OnInit {
	public pageViewPort: NeoComNode[] = [];

	// constructor() {
	// //	super();
	// }
	/**
  This is the equivalent entry point for the onCreate for an Android Activity. So its functionality is to define the activity layout and insert the fragments that will compose the Activity UI.
	The equivalence on Angular is that the .componet.html will define the layout so ther is no code required to set up that and that the fragments are already defined on the .html layout and being components on their own they will be initialized as this Page. So the functionality changes to load on the Activity/Page the model data required by the Fragments/Components to render the model structures.
	The Credential list will be accessed from the Service. If the list is not already there the Service will know how to get back that information from the backend. If already present we can connect the rendering Adapter directly with the model and use the collaborate2View to generate the list of components to render.
	*/
	ngOnInit() {
		// Set the variant identifier for this Page. This is a Fragment property but can be generalized for pages.
		this.setVariant(EVariant.CREDENTIALLIST)
		// Start the model-component transformation for the node elements returned by the Generator.
		getBodyParts(this.pageViewPort, this.appModelStore.accessCredentialList())

	}
	/**
	Return the list of nodes to be rendered on the Page viewPort. This is an equivalent to the connection between the DataSource, the Adapter and the ListView on the Android platform.
	*/
	public getBodyParts(): NeoComNode[] {
	}
}
