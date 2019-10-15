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

@Injectable({
    providedIn: 'root'
})
export class AuthorizationInterceptor implements HttpInterceptor {
    // - C O N S T R U C T O R
    constructor(protected isolationService: IsolationService) { }

    intercept(req: HttpRequest<any>,
        next: HttpHandler): Observable<HttpEvent<any>> {

        const idToken = this.isolationService.getFromStorage(this.isolationService.JWTTOKEN_KEY);

        if (idToken) {
            const cloned = req.clone({
                headers: req.headers.set("Authorization",
                    "Bearer " + idToken)
            });
            return next.handle(cloned);
        }
        else return next.handle(req);
    }
}
