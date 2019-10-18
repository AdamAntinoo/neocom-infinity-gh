export class NeoComResponse {
   private responseType: string;

   constructor(values: Object = {}) {
      Object.assign(this, values);
   }
   
   public getResponseType(): string {
      return this.responseType;
   }
}
