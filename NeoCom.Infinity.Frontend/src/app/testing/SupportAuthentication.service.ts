export class SupportAuthenticationService {
   private pass: boolean = false;
   private expired: boolean = false;

   public isLoggedIn(): boolean {
      if (this.pass)
         return !this.isExpiredToken();
      else return false;
   }
   public isExpiredToken(): boolean {
      return this.expired;
   }
   public setLogged(pass: boolean): void {
      this.pass = pass;
   }
   public setExpired(expired: boolean): void {
      this.expired = expired;
   }
}
