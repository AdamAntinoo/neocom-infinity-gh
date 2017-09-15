//--- INTERFACES
import { IDataSource } from '../classes/IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from '../classes/DataSourceLocator';
import { EVariant } from '../classes/EVariant.enumerated';
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from '../models/NeoComNode.model';
import { Pilot } from '../models/Pilot.model';
import { Region } from '../models/Region.model';

export class AbstractDataSource {
  static APPLICATION_SERVICE_PORT = "9000";
  static RESOURCE_SERVICE_URL: string = "http://localhost:" + AbstractDataSource.APPLICATION_SERVICE_PORT + "/api/v1";

  protected _serviceName: string = "AbstractDataSource";
  protected _canBeCached: boolean = true;
  protected _downloaded: boolean = false;
  protected _locator: DataSourceLocator = null;
  protected _variant: EVariant = EVariant.DEFAULT;
  protected _dataModelRoot: NeoComNode[] = [];
  protected _viewModelRoot: Render[] = [];

  public getLocator(): DataSourceLocator {
    return this._locator;
  }
  public getVariant(): EVariant {
    return this._variant;
  }
  public getVariantName(): string {
    return EVariant[this._variant];
  }
  public setLocator(locator: DataSourceLocator): void {
    this._locator = locator;
  }
  public setVariant(variant: EVariant): void {
    this._variant = variant;
  }
  public getServiceName(): string {
    return this._serviceName;
  }
  /**
  Read all the model nodes and generate a new list of their collaborations to the view list.
  */
  protected processModel(): Render[] {
    console.log("><[AbstractDataSource.processModel]");
    this._viewModelRoot = [];
    for (let node of this._dataModelRoot) {
      let collab = node.collaborate2View(this.getVariant());
      this._viewModelRoot = this._viewModelRoot.concat(collab);
    }
    return this._viewModelRoot;
  }
}
