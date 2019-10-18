// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
// - HTTP
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { HttpEvent } from '@angular/common/http';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
import { environment } from '@env/environment';

@Injectable({
   providedIn: 'root'
})
export class NeoComHeadersInterceptor implements HttpInterceptor {
   // - C O N S T R U C T O R
   constructor(protected isolationService: IsolationService) { }

   intercept(req: HttpRequest<any>,
      next: HttpHandler): Observable<HttpEvent<any>> {
      const cloned = req.clone({
         headers: req.headers
            .set('Content-Type', 'application/json; charset=utf-8')
            .set('Access-Control-Allow-Origin', '*')
            .set('xApp-Platform', 'Angular 8.0.x')
            .set('xApp-Name', environment.appName)
            .set('xApp-Version', environment.appVersion)
      });
      return next.handle(cloned);
   }
}
