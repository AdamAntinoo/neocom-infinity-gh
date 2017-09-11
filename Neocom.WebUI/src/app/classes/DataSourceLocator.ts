export class DataSourceLocator {
  private _locator: string = "";

  public addIdentifier(identifier: string): DataSourceLocator {
    this._locator += "."+identifier;
    return this;
  }
  public getLocator(): string {
    return this._locator;
  }
}
