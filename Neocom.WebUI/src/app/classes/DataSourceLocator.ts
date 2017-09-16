export class DataSourceLocator {
  private _locator: string = "";

  public addIdentifier(identifier: string): DataSourceLocator {
    this._locator += "." + identifier;
    return this;
  }
  public addNumberIdentifier(identifier: number): DataSourceLocator {
    return this.addIdentifier(identifier + '');
  }
  public getLocator(): string {
    return this._locator;
  }
}
