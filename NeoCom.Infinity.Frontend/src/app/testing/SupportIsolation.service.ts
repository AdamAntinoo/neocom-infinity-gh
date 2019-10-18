// - CORE
import { Injectable } from '@angular/core';
import { environment } from '@env/environment';

@Injectable({
   providedIn: 'root'
})
export class SupportIsolationService {
   private storage = new Map();
   private appName: string;
   private appVersion: string;

   // - E N V I R O N M E N T   A C C E S S
   //  public getAppName(): string {
   //      if (null == this.appName) return environment.appName;
   //      else return this.appName;
   //  }
   //  public getAppVersion(): string {
   //      if (null == this.appVersion) return environment.appVersion;
   //      else return this.appVersion;
   //  }
   //  public setAppName(value: string): string {
   //      this.appName = value;
   //      return this.appName;
   //  }
   //  public setAppVersion(value: string): string {
   //      this.appVersion = value;
   //      return this.appVersion;
   //  }

   // - E N V I R O N M E N T   A C C E S S
   //  public getServerName(): string {
   //      return environment.serverName;
   //  }
   //  public getApiV1(): string {
   //      return environment.apiVersion1;
   //  }
   //  public getApiV2(): string {
   //      return environment.apiVersion2;
   //  }
   //  public inDevelopment(): boolean {
   //      return !environment.production;
   //  }
   //  public getMockStatus(): boolean {
   //      return environment.mockStatus;
   //  }
   //  public showExceptions(): boolean {
   //      return environment.showexceptions;
   //  }

   // - S T O R A G E
   public getFromStorage(_key: string): any {
      return this.storage.get(_key);
   }
   public setToStorage(_key: string, _content: any): any {
      return this.storage.set(_key, _content)
   }
   public removeFromStorage(_key: string): void {
      this.storage.set(_key, null);
   }
   public getFromSession(_key: string): any {
      return this.storage.get(_key);
   }
   public setToSession(_key: string, _content: any): any {
      return this.storage.set(_key, _content)
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
}
