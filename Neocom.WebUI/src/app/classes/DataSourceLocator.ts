export class DataSourceLocator {
  private locator: string = "";

  public addIdentifier(identifier: string): DataSourceLocator {
    this.locator += identifier;
    return this;
  }
  public getLocator(): string {
    return this.locator;
  }
}
