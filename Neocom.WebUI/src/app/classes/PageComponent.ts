//--- INTERFACES
import { IPage } from './IPage.interface';
import { EVariant } from './EVariant.enumerated';

export class PageComponent implements IPage {
  public expanded: boolean = true;
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
    return false;
  }
  public hasMenu(): boolean {
    return false;
  }
}
