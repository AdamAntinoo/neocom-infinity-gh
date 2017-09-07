//--- INTERFACES
import { IDataSource } from './IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from './DataSourceLocator';
import { EVariant } from './EVariant.enumerated';
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from 'src/app/models/NeoComNode.model';

export class PilotListDataSource implements IDataSource {
  private _locator: DataSourceLocator = null;
  private _variant: EVariant = EVariant.DEFAULT;
  private: NeoComNode[] = [];
  private _viewModelRoot = null;

  constructor(locator: DataSourceLocator) {
    this._locator = locator;
  }
  public getLocator(): DataSourceLocator {
    return this._locator;
  }
  public getVariant(): EVariant {
    return this._variant;
  }
  public getVariantName(): string {
    return EVariant[this._variant];
  }
  public setVariant(variant: EVariant): void {
    this._variant = variant;
  }
  public collaborate2Model(): void {
    this._cookieService.put("login-id", "default")
    this.pilotRoasterService.getAllPilots()
      .subscribe(result => {
        console.log("--[PagePilotsComponent.ngOnInit.subscribe]> pilot list: " + JSON.stringify(result));
        // The the list of planatary resource lists to the data returned.
        this.pilotList = result;
      });
  }
  public collaborate2View(): Render[] {
    console.log(">>[PilotListDataSource.collaborate2View]");
    // Run over all nodes of the model hierarchy and collaborate each of them to the view.
    if (null == this._viewModelRoot) {
      this._viewModelRoot = new Render();
    } else {
      _partModelRoot.setModel(_dataModelRoot);
    }




    try {
      SpecialDataSource.logger.info(">> [SpecialDataSource.createContentHierarchy]");
      // Check if we have already a Part model.
      // But do not forget to associate the new Data model even of the old exists.

      SpecialDataSource.logger.info(
        "-- [SpecialDataSource.createContentHierarchy]> Initiating the refreshChildren() for the _partModelRoot");
      // Intercept any exception on the creation of the model but do not cut the progress of the already added items
      try {
        _partModelRoot.refreshChildren();
      } catch (Exception e) {
        e.printStackTrace();
      }
      // Get the list of Parts that will be used for the ListView
      _bodyParts = new ArrayList<IPart>();
      // Select for the body contents only the viewable Parts from the Part model. Make it a list.
      _bodyParts.addAll(_partModelRoot.collaborate2View());
    } catch (Exception e) {
      e.printStackTrace();
    }
    SpecialDataSource.logger
      .info("-- [SpecialDataSource.createContentHierarchy]> _bodyParts.size: " + _bodyParts.size());
    SpecialDataSource.logger.info("<< [SpecialDataSource.createContentHierarchy]");






    SpecialDataSource.logger.info(">> [PilotListDataSource.collaborate2Model]");
    //		AppModelStore store = AppModelStore.getSingleton();
    // The model contains the list of current registered api keys with their characters.
    HashMap < Integer, NeoComApiKey > keys = AppModelStore.getSingleton().getApiKeys();
    this.setDataModel(new RootNode());
    // Add all the characters to the new root
    for (NeoComApiKey key : keys.values()) {
      _dataModelRoot.addChild(key);
      SpecialDataSource.logger
        .info("-- [PilotListDataSource.collaborate2Model]> Adding " + key.getKey() + " to the _dataModelRoot");
    }
    SpecialDataSource.logger.info("<< [PilotListDataSource.collaborate2Model]");
    return _dataModelRoot;

  }
}
