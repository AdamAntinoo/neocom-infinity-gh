export class ErrorInfo{
   constructor(values: Object = {}) {
      Object.assign(this, values);
   }
}
export class ExceptionCatalog{
   static UNDEFINED_INSTANCE = new ErrorInfo({ code: 100, message: 'Trying to access an undefined application instance.' });
}
