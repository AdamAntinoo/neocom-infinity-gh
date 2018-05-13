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
// import { Component, OnInit } from '@angular/core';
// import { Router, ActivatedRoute, ParamMap } from '@angular/router';
// import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';
//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- INTERFACES
import { IViewer } from '../../../interfaces/IViewer.interface';
import { IDataSource } from '../../../interfaces/IDataSource.interface';
import { INeoComNode } from '../../../interfaces/INeoComNode.interface';
// import { PageComponent } from '../../../classes/PageComponent';
import { EVariant } from '../../../interfaces/EVariant.enumerated';
// import { DataSource } from '../../../models/DataSource.model';
// import { NeoComError } from '../../../classes/NeoComError';
//--- MODELS
import { NeoComNode } from '../../../models/NeoComNode.model';

/**
This component is the equivalent for the Part Factory at the Android platform. Its responsibility is to select the View component from the Model json class information. It also should control the variand as the real factory does but that feature is still in the drawing board.
This component will connect to the DataSource to link to the list instance where there is the list of model data to render on the UI. So any change on that list will send the right events to the UI components to redraw any changed node.
*/
@Component({
  selector: 'neocom-component-factory',
  templateUrl: './component-factory.component.html',
  styleUrls: ['./component-factory.component.scss']
})
export class ComponentFactoryComponent implements IViewer {
  // This is the connection with the Page. The DataSource will be able to generate the list of nodes to be rendered.
  @Input() dataSource: IDataSource;
  /** This is the local copy of the list of nodes to render. Once the list is calculates this reference if filled to fire the UI renderng refresh process. */
  private nodes: INeoComNode[] = null;

  /**
  Received the mouseenter event and then it has to send it to the page container through the selected component. The page container is represented by the DataSource.
  */
  public mouseEnter(target: NeoComNode) {
    this.dataSource.enterSelected(target);
  }
	/**
	Return the reference to the component that knows how to locate the Page to transmit the refresh events when any user action needs to update the UI.
	*/
  public getViewer(): IViewer {
    return this;
  }
	/**
	TODO: I have to test if this connection is necesary or if the UI detect automatically any generated event with keeping the connections in the code. The ruction is to reconstruct the list of nodes to be rendered and from that change update the UI.
	*/
  public notifyDataChanged(): void {
    this.nodes = null;
    this.dataSource.notifyDataChanged();
  }
	/**
	This is the connection method that will call the DataSource to get the list of nodes to render. This replicates the collaborate2View and getBodyParts functioanlities of the Android platform and will start the process to render the ui.
	*/
  public getPanelContents(): INeoComNode[] {
    if (null == this.dataSource) return this.nodes;
    if (null == this.nodes) this.nodes = this.dataSource.getBodyComponents();
    if (this.nodes.length < 1) this.nodes = this.dataSource.getBodyComponents();
    return this.nodes;
  }
}
