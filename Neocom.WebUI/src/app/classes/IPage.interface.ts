//--- INTERFACES
import { EVariant } from './EVariant.enumerated';

export interface IPage {
  getVariant(): EVariant;
  getVariantName(): string;
  setVariant(variant: EVariant): void;
}
