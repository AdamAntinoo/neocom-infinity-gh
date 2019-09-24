// - CORE
import { Injectable } from '@angular/core';
// - HTTP PACKAGE
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';

@Injectable({
    providedIn: 'root'
})
export class BackendService /*extends RuntimeBackendService*/ {
    private HEADERS;
    constructor(
        public isolation: IsolationService,
        protected http: HttpClient) {
        // super();
        // Initialize the list of header as a constant. Do this in code because the isolation dependency.
        this.HEADERS = new HttpHeaders()
            .set('Content-Type', 'application/json; charset=utf-8')
            .set('Access-Control-Allow-Origin', '*')
            // .set('xApp-Name', this.isolation.getAppName())
            // .set('xApp-version', this.isolation.getAppVersion())
            .set('xApp-Platform', 'Angular 6.1.x')
            .set('xApp-Brand', 'CitasCentro-Demo')
            .set('xApp-Signature', 'S0000.0011.0000');
    }
  // - E N V I R O N M E N T    C A L L S
  public getApplicationName(): string {
    return this.isolation.getAppName();
  }
  public getApplicationVersion(): string {
    return this.isolation.getAppVersion();
  }
  public inDevelopment(): boolean {
    return this.isolation.inDevelopment();
  }
  public showExceptions(): boolean {
    return this.isolation.showExceptions();
  }
}
