//--- CLASSES
import { DataSourceLocator } from './DataSourceLocator';
import { EVariant } from './EVariant.enumerated';
//--- MODELS
import { Render } from '../models/Render.model';
import { NeoComNode } from 'src/app/models/NeoComNode.model';

export interface IDataSource {
  //  f1(): NeoComNode[];
  getLocator(): DataSourceLocator;
  getVariant(): EVariant;
  getVariantName(): string;
  setVariant(variant: EVariant): void;
  collaborate2Model(): Promise<NeoComNode[]>;
  collaborate2View(): Render[];
}
