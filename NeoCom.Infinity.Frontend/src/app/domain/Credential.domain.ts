export class Credential {
   private uniqueId: string;
   private accountId: number;
   private accountName: string;
   private dataSource: string;
   private corporationId: number;

   constructor(values: Object = {}) {
      Object.assign(this, values);
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
