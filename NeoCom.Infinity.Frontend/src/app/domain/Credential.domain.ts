// - DOMAIN
import { NeoCom } from './NeoCom.domain';

export class Credential extends NeoCom {
   private uniqueId: string;
   private accountId: number;
   private accountName: string;
   private dataSource: string;
   private corporationId: number;

   constructor(values: Object = {}) {
      super();
      Object.assign(this, values);
      this.jsonClass = 'Credential';
   }

   public getAccountId(): number {
      return this.accountId;
   }
   public getAccountName(): string {
      return this.accountName;
   }
   public getUniqueId(): string {
      return this.uniqueId;
   }
   public getCorporationId(): number {
      return this.corporationId;
   }
}
