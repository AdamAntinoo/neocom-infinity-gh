import { NeoCom } from './NeoCom.domain';

export class Pilot extends NeoCom{
   public url4Icon: string;
   
   constructor(values: Object = {}) {
      super();
      Object.assign(this, values);
      this.jsonClass = 'Pilot';
   }
}
