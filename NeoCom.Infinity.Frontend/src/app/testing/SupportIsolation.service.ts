// - CORE
import { Injectable } from '@angular/core';
import * as jwt_decode from 'jwt-decode';

@Injectable({
   providedIn: 'root'
})
export class SupportIsolationService {
   private storage = new Map();

   // - S T O R A G E
   public getFromStorage(_key: string): any {
      return this.storage.get(_key);
   }
   public setToStorage(_key: string, _content: any): any {
      return this.storage.set(_key, JSON.stringify(_content))
   }
   public removeFromStorage(_key: string): void {
      this.storage.set(_key, null);
   }
   public getFromSession(_key: string): any {
      return this.storage.get(_key);
   }
   public setToSession(_key: string, _content: any): any {
      return this.storage.set(_key, JSON.stringify(_content))
   }
   public removeFromSession(_key: string): any {
      this.storage.set(_key, null);
   }

   // - R A M D O M
   public generateRandomNum(min: number, max: number) {
      return Math.floor(Math.random() * (max - min + 1)) + min;
   };
   public generateRandomString(length: number): string {
      var string = '';
      var letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz' //Include numbers if you want
      for (let i = 0; i < length; i++) {
         string += letters.charAt(Math.floor(Math.random() * letters.length));
      }
      return string;
   }

   // - J W T
   public JWTDecode(token: string): any {
      return jwt_decode(token);
   }
}
