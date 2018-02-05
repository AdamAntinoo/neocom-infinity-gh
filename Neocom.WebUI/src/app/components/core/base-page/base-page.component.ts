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
//--- MODELS
import { DataSource } from '../../../models/DataSource.model';
import { NeoComNode } from '../../../models/NeoComNode.model';

/**
This is the core code that is shared by all pages. Implements most of the code to deal with the model list data and the external request for data to be rendered on the UI.
*/
@Component({
	selector: 'neocom-base-page',
	templateUrl: './base-page.component.html',
	styleUrls: ['./base-page.component.css']
})
export class BasePageComponent /*extends PageComponent implements OnInit*/ extends DataSource {
	/** This exportable property will be used by the UI to know when to draw the spinner. */
	public downloading: boolean = false;
	/** This is the single pointer to the model data that is contained on this page. This is the first element than when processed with the collaborate2View process will generate the complete list of nodes to render and received by the factory from the getBodyComponents().
	This variable is accessed directly (never been null) and it if shared with all descendans during the generation process. */
	public dataModelRoot: NeoComNode[] = [];
	/** The real time updated list of nodes to render. */
	public renderNodeList: NeoComNode[] = [];
	/** This defines the rendering variant that can be used when collaborating nodes. */
	private _variant: EVariant = EVariant.DEFAULT;
	//	public expanded: boolean = false;
	//	private _downloaded: boolean = false;
	//	private _expandable: boolean = false;
	//	private _hasMenu: boolean = false;

	constructor(protected appModelStore: AppModelStoreService) {
		super();
	}

	// --- G E T T E R S   &   S E T T E R S
	public getVariant(): EVariant {
		return this._variant;
	}
	public getVariantName(): string {
		return EVariant[this._variant];
	}
	public setVariant(variant: EVariant): void {
		this._variant = variant;
	}

	//--- DATASOURCE INTERFACE
	public getBodyComponents(): NeoComNode[] {
		console.log(">>[CredentialDataSource.getBodyComponents]");
		// Clear the current list while reprocessing the new nodes.
		// TODO This should be improved to change only the nodes that change (replaced, removed, added)
		this.renderNodeList = [];
		// get the initial list by applying the policies defined at the page to the initial root node contents.
		let initialList = this.applyPolicies(this.dataModelRoot);
		// Generate the contents by collaborating to the view all the nodes.
		for (let node of this.dataModelRoot) {
			let nodes = node.collaborate2View(this.appModelStore, this.getVariant());
			console.log("--[CredentialDataSource.getBodyComponents]> Collaborating " + nodes.length + " nodes.");
			// Add the collaborated nodes to the list of nodes to return.
			for (let childNode of nodes) {
				this.renderNodeList.push(childNode);
			}
		}
		console.log("<<[CredentialDataSource.getBodyComponents]");
		return this.renderNodeList;
	}
	/**
	The common component to render any node has an input that is the specific DataSource for this page. This is the method to connect the local internal and specific DataSource to the generic rendel component.
	*/
	public getDataSource(): DataSource {
		return this;
	}
	private applyPolicies(contents: NeoComNode[]): NeoComNode[] {
		return contents;
	}

	// public isExpandable(): boolean {
	// 	return this._expandable;
	// }
	// public hasMenu(): boolean {
	// 	return this._hasMenu;
	// }
	// public setExpandable(flag: boolean): boolean {
	// 	this._expandable = flag;
	// 	return this._expandable;
	// }

	// --- A C T I O N S
	// public onClickArrow(): void {
	// 	this.expanded = !this.expanded;
	// }
	// 	// This is a method that should be implemented by all dependents.
	// public refreshViewPort(): void {
	// }
	//
	// ngOnInit() {
	// }

}
