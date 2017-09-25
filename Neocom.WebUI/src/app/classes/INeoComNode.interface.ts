import { Observable } from 'rxjs/Rx';

//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';

export interface INeoComNode {
  collaborate2View(variant: EVariant): NeoComNode[];
  //  toggleExpanded();
}
