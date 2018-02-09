//--- INTERFACES
import { IPage } from './IPage.interface';
import { EVariant } from './EVariant.enumerated';

export class PageComponent implements IPage {
  public downloading: boolean = false;
  public expanded: boolean = false;
  private _expandable: boolean = false;
  private _variant: EVariant = EVariant.DEFAULT;

  public getVariant(): EVariant {
    return this._variant;
  }
  public getVariantName(): string {
    return EVariant[this._variant];
  }
  public setVariant(variant: EVariant): void {
    this._variant = variant;
  }
  public onClickArrow(): void {
    this.expanded = !this.expanded;
  }
  public isExpandable(): boolean {
    return this._expandable;
  }
  public setExpandable(flag: boolean): boolean {
    this._expandable = flag;
    return this._expandable;
  }
  public hasMenu(): boolean {
    return false;
  }
  // This is a method that should be implemented by all dependents.
  public refreshViewPort(): void {
  }
  public notifyDataChanged(): void { }
}
