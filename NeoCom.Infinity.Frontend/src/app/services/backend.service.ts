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
import { HttpClientWrapperService } from './httpclientwrapper.service';
// - DOMAIN
import { ValidateAuthorizationTokenResponse } from '@app/domain/dto/ValidateAuthorizationTokenResponse';
import { NeoComResponse } from '@app/domain/dto/NeoComResponse';
import { NeoComException } from '@app/platform/NeoComException';
import { ServerInfoResponse } from '@app/domain/dto/ServerInfoResponse.dto';
import { CorporationDataResponse } from '@app/domain/dto/CorporationDataResponse.dto';
import { Pilot } from '@app/domain/Pilot.domain';
import { ResponseTransformer } from './support/ResponseTransformer';
import { Corporation } from '@app/domain/Corporation.domain';
import { ServerStatus } from '@app/domain/ServerStatus.domain';

@Injectable({
    providedIn: 'root'
})
export class BackendService {
    private HEADERS;
    private APIV1: string;

    constructor(
        public isolation: IsolationService,
        protected httpService: HttpClientWrapperService,
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
        this.APIV1 = environment.serverName + environment.apiVersion1;
    }

    // - B A C K E N D - A P I
    public apiValidateAuthorizationToken_v1(code: string, state: string): Observable<ValidateAuthorizationTokenResponse> {
        console.log(">[BackendService.apiValidateAuthorizationToken_v1]> code: " + code);
        // Construct the request to call the backend.
        const request = this.APIV1 + "/validateAuthorizationToken" +
            "?code=" + code +
            "&state=" + state +
            "&datasource=" + environment.ESIDataSource.toLowerCase();
        return this.httpService.wrapHttpGETCall(request)
            .pipe(map((data: any) => {
                const response = this.transformApiResponse(data, request) as ValidateAuthorizationTokenResponse;
                return response;
            }));
    }
    public apiGetServerInfo_v1(transformer: ResponseTransformer): Observable<ServerStatus> {
        const request = this.APIV1 + "/server/datasource/" + environment.ESIDataSource.toLowerCase();
        return this.httpService.wrapHttpGETCall(request)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiGetServerInfo_v1]> Transformation: " +
                    transformer.description);
                const response = transformer.transform(data) as ServerStatus;
                return response;
            }));
    }
    public apiGetCorporationPublicData_v1(corporationId: number, transformer: ResponseTransformer): Observable<Corporation> {
        const request = this.APIV1 + "/corporations/" + corporationId;
        return this.httpService.wrapHttpGETCall(request)
            .pipe(map((data: any) => {
                console.log(">[BackendService.apiGetCorporationPublicData_v1]> Transformation: " +
                    transformer.description);
                const response = transformer.transform(data) as Corporation;
                return response;
            }));
    }
    public apiGetPilotPublicData_v1(pilotId: number): Observable<Pilot> {
        const request = this.APIV1 + "/pilots/" + pilotId;
        return this.httpService.wrapHttpGETCall(request)
            .pipe(map((data: any) => {
                const response = new Pilot(data);
                return response;
            }));
    }

    // -  H T T P   W R A P P E R S
    //    private wrapHttpHandleError(error: HttpErrorResponse) {
    //       if (error.error instanceof ErrorEvent) {
    //          // A client-side or network error occurred. Handle it accordingly.
    //          console.error('An error occurred:', error.error.message);
    //       } else {
    //          // The backend returned an unsuccessful response code.
    //          // The response body may contain clues as to what went wrong,
    //          console.error(
    //             `Backend returned code ${error.status}, ` +
    //             `Error message ${error.message}, `);

    //          // Do some generic error processing.
    //          // 401 are accesses without the token so we should move right to the login page.
    //          if (error.status == 401) {
    //             // this.router.navigate(['login']);
    //             return throwError('Autenticacion ya no valida. Es necesario logarse de nuevo.');
    //          }
    //       }
    //       // return an observable with a user-facing error message
    //       return throwError(
    //          'Something bad happened; please try again later.');
    //    }
    //    public wrapHttpRESOURCECall(request: string): Observable<any> {
    //       console.log("><[AppStoreService.wrapHttpGETCall]> request: " + request);
    //       return this.http.get(request);
    //    }
    //    /**
    //     * This method wraps the HTTP access to the backend. It should add any predefined headers, any request specific headers and will also deal with mock data.
    //     * Mock data comes now into two flavours. he first one will search for the request on the list of defined requests (if the mock is active). If found then it will check if the request should be sent to the file system ot it should be resolved by accessing the LocalStorage.
    //     * @param  request [description]
    //     * @return         [description]
    //     */
    //    public wrapHttpGETCall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
    //       let adjustedRequest = this.wrapHttpSecureRequest(_request);
    //       if (typeof adjustedRequest === 'string') {
    //          // The request should continue with processing.
    //          console.log("><[AppStoreService.wrapHttpGETCall]> request: " + adjustedRequest);
    //          let newheaders = this.wrapHttpSecureHeaders(_requestHeaders);
    //          return this.http.get(adjustedRequest, { headers: newheaders });
    //       } else {
    //          // The requst is a LOCALSTORAGE mockup and shoudl return with no more processing.
    //          console.log("><[AppStoreService.wrapHttpGETCall]> request: " + _request);
    //          return adjustedRequest;
    //       }
    //    }
    //    public wrapHttpPUTCall(_request: string, _body: string, _requestHeaders?: HttpHeaders): Observable<any> {
    //       let adjustedRequest = this.wrapHttpSecureRequest(_request);
    //       if (typeof adjustedRequest === 'string') {
    //          // The request should continue with processing.
    //          console.log("><[AppStoreService.wrapHttpPUTCall]> request: " + adjustedRequest);
    //          console.log("><[AppStoreService.wrapHttpPUTCall]> body: " + _body);
    //          let newheaders = this.wrapHttpSecureHeaders(_requestHeaders);
    //          return this.http.put(adjustedRequest, _body, { headers: newheaders });
    //          // .pipe(
    //          //   catchError(this.wrapHttpHandleError)
    //          // );
    //       } else {
    //          // The requst is a LOCALSTORAGE mockup and shoudl return with no more processing.
    //          console.log("><[AppStoreService.wrapHttpPUTCall]> request: " + _request);
    //          return adjustedRequest;
    //       }
    //    }
    //    public wrapHttpPOSTCall(_request: string, _body: string, _requestHeaders?: HttpHeaders): Observable<any> {
    //       let adjustedRequest = this.wrapHttpSecureRequest(_request);
    //       if (typeof adjustedRequest === 'string') {
    //          // The request should continue with processing.
    //          console.log("><[AppStoreService.wrapHttpPOSTCall]> request: " + adjustedRequest);
    //          console.log("><[AppStoreService.wrapHttpPOSTCall]> body: " + _body);
    //          let newheaders = this.wrapHttpSecureHeaders(_requestHeaders);
    //          return this.http.post(adjustedRequest, _body, { headers: newheaders });
    //          // .pipe(
    //          //   catchError(this.wrapHttpHandleError)
    //          // );
    //       } else {
    //          // The requst is a LOCALSTORAGE mockup and shoudl return with no more processing.
    //          console.log("><[AppStoreService.wrapHttpPOSTCall]> request: " + _request);
    //          return adjustedRequest;
    //       }
    //    }
    //    public wrapHttpDELETECall(_request: string, _requestHeaders?: HttpHeaders): Observable<any> {
    //       let adjustedRequest = this.wrapHttpSecureRequest(_request);
    //       if (typeof adjustedRequest === 'string') {
    //          // The request should continue with processing.
    //          console.log("><[AppStoreService.wrapHttpDELETECall]> request: " + adjustedRequest);
    //          let newheaders = this.wrapHttpSecureHeaders(_requestHeaders);
    //          return this.http.delete(adjustedRequest, { headers: newheaders });
    //       } else {
    //          // The request is a LOCALSTORAGE mockup and should return with no more processing.
    //          console.log("><[AppStoreService.wrapHttpDELETECall]> request: " + _request);
    //          return adjustedRequest;
    //       }
    //    }

    //    protected wrapHttpSecureRequest(request: string): string | Observable<any> {
    //       // Check if we should use mock data.
    //       // if (this.getMockStatus()) {
    //       //     // Search for the request at the mock map.
    //       //     let hit = this.responseTable[request];
    //       //     if (null != hit) {
    //       //         // Check if the resolution should be from the LocalStorage. URL should start with LOCALSTORAGE::.
    //       //         if (hit.search('LOCALSTORAGE::') > -1) {
    //       //             return Observable.create((observer) => {
    //       //                 let targetData = this.getFromStorage(hit + ':' + this.accessCredential().getId());
    //       //                 try {
    //       //                     if (null != targetData) {
    //       //                         // .then((data) => {
    //       //                         console.log('--[AppStoreService.wrapHttpPOSTCall]> Mockup data: ', targetData);
    //       //                         // Process and convert the data string to the class instances.
    //       //                         let results = this.transformRequestOutput(JSON.parse(targetData));
    //       //                         observer.next(results);
    //       //                         observer.complete();
    //       //                     } else {
    //       //                         observer.next([]);
    //       //                         observer.complete();
    //       //                     }
    //       //                 } catch (mockException) {
    //       //                     observer.next([]);
    //       //                     observer.complete();
    //       //                 }
    //       //             })
    //       //         } else request = hit;
    //       //     }
    //       // }
    //       return request;
    //    }
    //    /**
    //     * This is the common code to all secure calls. It will check if the call can use the mockup system and if that system has a mockup destionation for the request.
    //     * This call also should create a new set of headers to be used on the next call and should put inside the current authentication data.
    //     *
    //     * @protected
    //     * @param {string} request
    //     * @param {string} _body
    //     * @param {HttpHeaders} [_requestHeaders]
    //     * @returns {HttpHeaders}
    //     * @memberof BackendService
    //     */
    //    protected wrapHttpSecureHeaders(_requestHeaders?: HttpHeaders): HttpHeaders {
    //       let headers = new HttpHeaders()
    //          .set('Content-Type', 'application/json; charset=utf-8')
    //          .set('Access-Control-Allow-Origin', '*')
    //          .set('xApp-Name', environment.appName)
    //          .set('xApp-Version', environment.appVersion)
    //          .set('xApp-Platform', environment.platform)
    //       // Add authentication token but only for authorization required requests.
    //       // let cred = this.accessCredential();
    //       // if (null != cred) {
    //       //     let auth = this.accessCredential().getAuthorization();
    //       //     if (null != auth) headers = headers.set('xApp-Authentication', auth);
    //       //     console.log("><[AppStoreService.wrapHttpSecureHeaders]> xApp-Authentication: " + auth);
    //       // }
    //       if (null != _requestHeaders) {
    //          for (let key of _requestHeaders.keys()) {
    //             headers = headers.set(key, _requestHeaders.get(key));
    //          }
    //       }
    //       return headers;
    //    }

    // - R E S P O N S E   T R A N S F O R M A T I O N
    // public transformRequestOutput(entrydata: any): INode[] | INode {
    //     let results: INode[] = [];
    //     // Check if the entry data is a single object. If so process it because can be an exception.
    //     if (entrydata instanceof Array) {
    //         for (let key in entrydata) {
    //             // Access the object into the spot.
    //             let node = entrydata[key] as INode;
    //             // Convert and add the node.
    //             results.push(this.convertNode(node));
    //         }
    //     } else {
    //         // Process a single element.
    //         let jclass = entrydata["jsonClass"];
    //         if (null == jclass) return [];
    //         return this.convertNode(entrydata);
    //     }
    //     return results;
    // }
    private transformApiResponse(responseData: any, requestUrl: string): NeoComResponse {
        let responseType = responseData["responseType"];
        switch (responseType) {
            case 'ValidateAuthorizationTokenResponse':
                return new ValidateAuthorizationTokenResponse(responseData);
            case 'ServerInfoResponse':
                return new ServerInfoResponse(responseData);
            case 'CorporationDataResponse':
                return new CorporationDataResponse(responseData);
            default:
                throw new NeoComException({
                    "ok": false,
                    status: 400,
                    statusText: "Unidentified response",
                    message: "Api response type does not match any of the declared types. Invalid response",
                    requestUrl: requestUrl
                });
        }
    }
}
