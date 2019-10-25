// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { retry } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
// - HTTPCLIENT
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { HttpEvent } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
// - DOMAIN
import { NeoComException } from '@app/platform/NeoComException';
import { AppStoreService } from '@app/services/appstore.service';

@Injectable({
    providedIn: 'root'
})
export class HttpErrorInterceptor implements HttpInterceptor {
    constructor(protected appStoreService: AppStoreService) { }
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request)
            .pipe(
                retry(1),
                catchError((error: HttpErrorResponse) => {
                    let errorMessage = '';
                    if (error.error instanceof ErrorEvent) {
                        // client-side error
                        errorMessage = `Error: ${error.error.message}`;
                    } else {
                        // server-side error
                        errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
                        this.exceptionProcessing(error);
                    }
                    window.alert(errorMessage);
                    return throwError(errorMessage);
                })
            )
    }
    private exceptionProcessing(error: HttpErrorResponse) {
        // Convert the error to an application exception.
        let exception = new NeoComException({
            status: error.status,
            message: error.message,
        });
        switch (exception.status) {
            case 400:
                exception.statusText = 'BAD_REQUEST';
                exception.setUserMessage('The server reported an exception and not accepted the request. Please login again.')
                break;
        }
        this.appStoreService.setLastInterceptedException(exception);
        this.appStoreService.route2Destination('exceptionInfo');
    }
}
