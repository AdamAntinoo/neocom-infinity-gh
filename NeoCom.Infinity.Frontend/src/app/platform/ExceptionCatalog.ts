export class ErrorInfo {
   constructor(values: Object = {}) {
      Object.assign(this, values);
   }
}
export class ExceptionCatalog {
   static UNDEFINED_INSTANCE = new ErrorInfo(
      {
         code: 100,
         message: 'Trying to access an undefined application instance.'
      });
   static AUTHORIZATION_MISSING = new ErrorInfo(
      {
         code: 101,
         message: 'The authorized credential is not present. Application requires new authentication.'
      });
}
