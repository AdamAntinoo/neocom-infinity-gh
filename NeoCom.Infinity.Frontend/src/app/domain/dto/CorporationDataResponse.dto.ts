// - DOMAIN
import { Corporation } from '../Corporation.domain';
import { Pilot } from '../Pilot.domain';
import { NeoComResponse } from './NeoComResponse';

export class CorporationDataResponse extends NeoComResponse {
   public corporation: Corporation;
   public ceoPilotData: Pilot;
}
