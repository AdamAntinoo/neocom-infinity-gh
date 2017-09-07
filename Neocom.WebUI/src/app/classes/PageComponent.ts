//--- INTERFACES
import { IPage } from './IPage.interface';
import { EVariant } from './EVariant.enumerated';

export class PageComponent implements IPage {
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
}
