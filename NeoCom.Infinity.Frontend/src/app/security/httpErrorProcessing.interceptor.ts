// - COMMON
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { retry } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
// import { throwError } from 'rxjs/operators';
// - HTTPCLIENT
import { HttpInterceptor } from '@angular/common/http';
import { HttpRequest } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { HttpEvent } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';

export class HttpErrorInterceptor implements HttpInterceptor {
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
                    }
                    window.alert(errorMessage);
                    return throwError(errorMessage);
                })
            )
    }
}
