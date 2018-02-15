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
import { Observable } from 'rxjs/Rx';
// Import RxJs required methods
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- INTERFACES
import { IDataSource } from '../classes/IDataSource.interface';
import { INeoComNode } from '../classes/INeoComNode.interface';
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
import { EveItem } from '../models/EveItem.model';
import { FittingItem } from '../models/FittingItem.model';

export abstract class PageDataSource implements IDataSource {
  /** This exportable property will be used by the UI to know when to draw/hide the spinner. */
  public downloading: boolean = false;
	/** This is the single pointer to the model data that is contained on this page. This is the first element than when processed with the collaborate2View process will generate the complete list of nodes to render and received by the factory from the getBodyComponents().
	This variable is accessed directly (never been null) and it if shared with all descendans during the generation process. */
  public dataModelRoot: NeoComNode[] = [];
  /** The real time updated list of nodes to render. */
  public renderNodeList: NeoComNode[] = [];
  /** This defines the rendering variant that can be used when collaborating nodes. */
  private _variant: EVariant = EVariant.DEFAULT;

  constructor(protected appModelStore: AppModelStoreService) {
    // super(appModelStore);
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
	/**
	The common component to render any node has an input that is the specific DataSource for this page. This is the method to connect the local internal and specific DataSource to the generic rendel component.
	*/
  public getDataSource(): IDataSource {
    return this;
  }

  //--- IDATASOURCE INTERFACE
  public abstract applyPolicies(contents: NeoComNode[]): NeoComNode[];
  public abstract enterSelected(target: NeoComNode);
	/**
	Reconstructs the list of nodes to be rendered from the current DataRoot and their collaborations to the view.
	*/
  public notifyDataChanged(): void {
    console.log(">>[PageDataSource.notifyDataChanged]");
    // Clear the current list while reprocessing the new nodes.
    // TODO This should be improved to change only the nodes that change (replaced, removed, added)
    // this.renderNodeList = [];
    let copyList = [];
    // Get the initial list by applying the policies defined at the page to the initial root node contents. Policies may be sorting or filtering actions.
    let initialList = this.applyPolicies(this.dataModelRoot);
    // Generate the contents by collaborating to the view all the nodes.
    for (let node of initialList) {
      let nodes = node.collaborate2View(this.appModelStore, this.getVariant());
      console.log("--[PageDataSource.notifyDataChanged]> Collaborating " + nodes.length + " nodes.");
      // Add the collaborated nodes to the list of nodes to return.
      for (let childNode of nodes) {
        copyList.push(childNode);
      }
    }
    this.renderNodeList = copyList;
    console.log("<<[PageDataSource.notifyDataChanged]");
  }
  /** Just connects the list and returns a pointer to it before signaling an update. */
  public getBodyComponents(): NeoComNode[] {
    console.log("><[PageDataSource.getBodyComponents]");
    this.notifyDataChanged();
    return this.renderNodeList;
  }
}
