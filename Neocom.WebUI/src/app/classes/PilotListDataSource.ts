//--- INTERFACES
import { IDataSource } from './IDataSource.interface';
//--- CLASSES
import { DataSourceLocator } from './DataSourceLocator';

export class PilotListDataSource implements IDataSource {
  private locator: DataSourceLocator = null;
  private variant: string = "";

  constructor(locator: DataSourceLocator) {
    this.locator = locator;
  }
  public getLocator(): DataSourceLocator {
    return this.locator;
  }
  public getVariant(): string {
    return this.variant;
  }
  public setVariant(variant: string): void {
    this.variant = variant;
  }
}
