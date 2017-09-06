//--- CLASSES
import { DataSourceLocator } from './DataSourceLocator';
//import { NeoComNode } from '../models/NeoComNode.model';
export interface IDataSource {
  //  f1(): NeoComNode[];
  getLocator(): DataSourceLocator;
  getVariant(): string;
  setVariant(variant: string): void;
}
