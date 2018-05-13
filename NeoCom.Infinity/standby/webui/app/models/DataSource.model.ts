//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms
//               , the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code maid in typescript within the Angular
//               framework.
//--- INTERFACES
//import { PageComponent } from '../../../classes/PageComponent';
// import { EVariant } from '../../../classes/EVariant.enumerated';
// import { DataSource } from '../../../classes/DataSource';
import { NeoComError } from '../classes/NeoComError';
//--- MODELS
import { NeoComNode } from './NeoComNode.model';
//--- INTERFACES
import { IPage } from '../classes/IPage.interface';
import { EVariant } from '../classes/EVariant.enumerated';
import { PageComponent } from '../classes/PageComponent';

export class DataSource {
	public selectedNode: NeoComNode = null;

	public getBodyComponents(): NeoComNode[] {
		return [];
	}
	/**
	This item has the mouse over it. Signal that to the viewer to report the detailed.
	*/
	public enterSelected(selected: NeoComNode) {
		this.selectedNode = selected;
	}
}
