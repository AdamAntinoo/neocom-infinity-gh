// - CORE
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '@env/environment';
// - HTTP PACKAGE
import { HttpClient } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';

@Injectable({
    providedIn: 'root'
})
export class HttpClientWrapperService {

    constructor(protected http: HttpClient) { }

    // -  H T T P   W R A P P E R S
    /**
     * This method wraps the HTTP access to the backend. It should add any predefined headers, any request specific headers and will also deal with mock data.
     * Mock data comes now into two flavours. The first one will search for the request on the list of defined requests (if the mock is active). If found then it will check if the request should be sent to the file system ot it should be resolved by accessing the LocalStorage.
     * @param  request [description]
     * @return         [description]
     */
    public wrapHttpGETCall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpGETCall]> request: " + _request);
        let newheaders = this.wrapHttpSecureHeaders(_requestHeaders);
        return this.http.get(_request, { headers: newheaders });
    }
    /**
     * Reads a JSON formatted resource. There is no specific convertion to a types class and so can be done on the caller method.
     * @param request the location of the resource file to be read. The resource starts on the /assets/properties location.
     */
    public wrapHttpRESOURCECall(request: string): Observable<any> {
        console.log("><[HttpClientWrapperService.wrapHttpRESOURCECall]> request: " + request);
        return this.http.get(request);
    }

    /**
     * This is the common code to all secure calls. It will check if the call can use the mockup system and if that system has a mockup destionation for the request.
     * This call also should create a new set of headers to be used on the next call and should put inside the current authentication data.
     *
     * @protected
     * @param {HttpHeaders} [_requestHeaders]
     * @returns {HttpHeaders}
     * @memberof BackendService
     */
    protected wrapHttpSecureHeaders(_requestHeaders?: HttpHeaders): HttpHeaders {
        let headers = new HttpHeaders()
            .set('Content-Type', 'application/json; charset=utf-8')
            .set('xApp-Name', environment.appName)
            .set('xApp-Version', environment.appVersion)
            .set('xApp-Platform', environment.platform)
            .set('xApp-Signature', 'S0000.0016.0001')
            .set('xApp-Signature', 'S0000.0019.0001');
        if (null != _requestHeaders) { // Copy in additional headers.
            for (let key of _requestHeaders.keys()) {
                headers = headers.set(key, _requestHeaders.get(key));
            }
        }
        return headers;
    }
}
