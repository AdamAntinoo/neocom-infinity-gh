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
import { EVariant } from '../../classes/EVariant.enumerated';
import { PageComponent } from '../../classes/PageComponent';
//--- MODELS
//import { Login } from '../../models/Login.model';
import { DataSource } from '../../models/DataSource.model';
import { NeoComNode } from '../../models/NeoComNode.model';
import { Credential } from '../../models/Credential.model';
//--- COMPONENTS
import { BasePageComponent } from '../../components/core/base-page/base-page.component';

/**
This is the Credentials page. I am using it as the test bed for the rest of the development until I have a clear view of how to implement the Dashboard and the new authentication login mechanism so I can use this on a Corporation environment.
The work flow that is the flow to be followed by all other pages is that on the OnInit event I get the list of contents for the data model list (equivalence of _dataModelRoot on Android). With that list the UI component generator factory will be able to run the collaborate2View on the contents and render any node it finds.
Pages on Angular are the equivalent functionality as Activity+Fragment+DataSource+Generator on Android.
*/
@Component({
	selector: 'neocom-credentials-page',
	templateUrl: './credentials-page.component.html',
	styleUrls: ['./credentials-page.component.css']
})
export class CredentialsPageComponent extends BasePageComponent implements OnInit, DataSource {
	//	public pageViewPort: NeoComNode[] = [];
	//	private credentialList: NeoComNode[] = [];

	// constructor() {
	// //	super();
	// }
	/**
  This is the equivalent entry point for the onCreate for an Android Activity. So its functionality is to define the activity layout and insert the fragments that will compose the Activity UI.
	The equivalence on Angular is that the .componet.html will define the layout so ther is no code required to set up that and that the fragments are already defined on the .html layout and being components on their own they will be initialized as this Page. So the functionality changes to load on the Activity/Page the model data required by the Fragments/Components to render the model structures.
	The Credential list will be accessed from the Service. If the list is not already there the Service will know how to get back that information from the backend. If already present we can connect the rendering Adapter directly with the model and use the collaborate2View to generate the list of components to render.
	*/
	ngOnInit() {
		console.log(">> [CredentialsPageComponent.ngOnInit]");
		// Set the variant identifier for this Page. This is a Fragment property but can be generalized for pages.
		this.setVariant(EVariant.CREDENTIALLIST)
		// Start the model-component transformation for the node elements returned by the Generator.
		// Get the model elements from the Service.
		this.downloading = true;
		// Call the service to get the list of Logins.
		this.appModelStore.accessCredentialList()
			.subscribe(result => {
				console.log("--[LoginPageComponent.ngOnInit.accessLoginList]> Loginlist.length: " + result.length);
				this.dataModelRoot = result;
				// Sort the list of Logins before processing their collaborations.
				//			let sortedLogins = this.sortLogins(result);
				// Loop over all the returned items.
				// 			for (let node of result) {
				// 				// Add to the result only the Logins with at least one character.
				// //				if (node.getKeyCount() > 0) {
				// 					let theList = node.collaborate2View(this.appModelStore, this.getVariant());
				// 					this.loginViewList = this.loginViewList.concat(theList);
				// //				}
				// 			}
				this.downloading = false;
			});
		console.log("<< [CredentialsPageComponent.ngOnInit]");
	}
	/**
Return the list of nodes to be rendered on the Page viewPort. This is an equivalent to the connection between the DataSource, the Adapter and the ListView on the Android platform.
*/
}
// export class CredentialDataSource extends DataSource {
// 	constructor(private credentialList: NeoComNode[]) {
// 		super();
// 	}
// 	// public getBodyParts(): NeoComNode[] {
// 	// 	console.log(">>[CredentialDataSource.getBodyParts]");
// 	// 	let nodeList: NeoComNode[] = [];
// 	// 	// Generate the contents by collaborating to the view all the nodes.
// 	// 	for (let node of this.credentialList) {
// 	// 		let nodes = node.collaborate2View(appModelStore, variant);
// 	// 		console.log(">>[CredentialDataSource.getBodyParts]> Collaborating "+nodes.length+" nodes.");
// 	// 	}
// 	// 	return nodeList;
// 	// }
// }
