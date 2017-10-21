import { Observable } from 'rxjs/Rx';

//--- SERVICES
import { AppModelStoreService } from '../services/app-model-store.service';
//--- MODELS
import { NeoComNode } from '../models/NeoComNode.model';
//--- INTERFACES
import { EVariant } from '../classes/EVariant.enumerated';


export interface INeoComNode {
  //  collaborate2View(variant: EVariant): NeoComNode[];
  collaborate2View(appModelStore: AppModelStoreService, variant: EVariant): NeoComNode[];
  //  toggleExpanded();
}
