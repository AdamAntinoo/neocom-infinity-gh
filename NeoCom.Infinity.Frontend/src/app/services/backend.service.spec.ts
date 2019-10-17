// - CORE
import { NO_ERRORS_SCHEMA } from '@angular/core';
// - HTTP PACKAGE
import { HttpClientModule } from '@angular/common/http';
import { HttpClient } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { HttpTestingController } from '@angular/common/http/testing';
// import { HttpHandler } from '@angular/common/http';
// import { HttpErrorResponse } from '@angular/common/http';
// import { HttpHeaders } from '@angular/common/http';
// - WEBSTORAGE
import { LOCAL_STORAGE } from 'angular-webstorage-service';
import { SESSION_STORAGE } from 'angular-webstorage-service';
import { WebStorageService } from 'angular-webstorage-service';
// - TESTING
import { async } from '@angular/core/testing';
import { inject } from '@angular/core/testing';
import { fakeAsync } from '@angular/core/testing';
import { tick } from '@angular/core/testing';
import { ComponentFixture } from '@angular/core/testing';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { RouteMockUpComponent } from '@app/testing/RouteMockUp.component';
import { routes } from '@app/testing/RouteMockUp.component';
// - PROVIDERS
import { IsolationService } from '@app/platform/isolation.service';
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
import { BackendService } from './backend.service';
import { ValidateAuthorizationTokenResponse } from '@app/domain/dto/ValidateAuthorizationTokenResponse';
import { ServerInfoResponse } from '@app/domain/dto/ServerInfoResponse.dto';

describe('SERVICE BackendService [Module: APP]', () => {
    // let component: AuthenticationService;
    // let fixture: ComponentFixture<AuthenticationService>;
    let service: BackendService;

    beforeEach(() => {
        TestBed.configureTestingModule({
            schemas: [NO_ERRORS_SCHEMA],
            imports: [
                RouterTestingModule.withRoutes(routes),
                HttpClientModule,
                HttpClientTestingModule
            ],
            declarations: [
                RouteMockUpComponent,
            ],
            providers: [
                { provide: IsolationService, useClass: SupportIsolationService },
                // { provide: HttpClient, useClass: HttpClient },
                // { provide: HttpHandler, useClass: HttpHandler }
            ]
        })
            .compileComponents();
        service = TestBed.get(BackendService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[app/BackendService]> should be created');
            expect(service).toBeTruthy('component has not been created.');
        });
    });

    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [transformApiResponse]', () => {
        it('transformApiResponse.ValidateAuthorizationTokenResponse: validate the transformation for a ValidateAuthorizationTokenResponse', () => {
            const validationResponseJson = {
                "responseType": "ValidateAuthorizationTokenResponse",
                "jwtToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTgzODQ3MjYsImFjY291bnROYW1lIjoiQmV0aCBSaXBsZXkiLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIyMjM2NDciLCJwaWxvdElkIjo5MjIyMzY0N30.Qom8COyZB2sW3bCGm9pnGcIOqw-E2yKDsmGklQW6r9Fhu8jJpkNUv5TUhU2cJjIg5jX3082bZ6eKtRZ3z10vGw",
                "credential": {
                    "jsonClass": "Credential",
                    "uniqueId": "tranquility/92223647",
                    "accountId": 92223647,
                    "accountName": "Adam Antinoo",
                    "assetsCount": 1476,
                    "walletBalance": 6.309543632E8,
                    "raceName": "Amarr",
                    "dataSource": "tranquility"
                }
            };
            const validationResponse = new ValidateAuthorizationTokenResponse(validationResponseJson);
            const serviceAsAny = service as any;
            const obtained = serviceAsAny.transformApiResponse(validationResponseJson);
            expect(JSON.stringify(obtained)).toBe(JSON.stringify(validationResponse));
        });
        it('transformApiResponse.ServerInfoResponse: validate the transformation for a ServerInfoResponse', () => {
            const serverResponseJson = {
                "responseType": "ServerInfoResponse",
                "players": 12528,
                "server_version": "1585794",
                "start_time": "2019-10-15T11:06:17Z"
            };
            const serverResponse = new ServerInfoResponse(serverResponseJson);
            const serviceAsAny = service as any;
            const obtained = serviceAsAny.transformApiResponse(serverResponseJson);
            expect(JSON.stringify(obtained)).toBe(JSON.stringify(serverResponse));
        });
        it('transformApiResponse.failure: get an exception if the data does not match', () => {
            const undefinedResponseJson = {
                "responseType": "UndefinedResponse"
            };
            // const serverResponse = new ServerInfoResponse(serverResponseJson);
            const serviceAsAny = service as any;
            try {
                const obtained = serviceAsAny.transformApiResponse(undefinedResponseJson);
            } catch (neocomException) {
                expect(neocomException).toBeDefined();
                expect(neocomException.status).toBe(400);
                expect(neocomException.statusText).toBe("Unidentified response");
                expect(neocomException.message).toBe("Api response type does not match any of the declared types. Invalid response");
            }
        });
    });
    xdescribe('Code Coverage Phase [apiValidateAuthorizationToken_v1]', () => {
        it('apiValidateAuthorizationToken_v1.success: get a validated authorization from the mocked server',
            async(inject([HttpClient, HttpTestingController], (http: HttpClient, backend: HttpTestingController) => {
                const validationResponseJson = {
                    "responseType": "ValidateAuthorizationTokenResponse",
                    "jwtToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJFU0kgT0F1dGgyIEF1dGhlbnRpY2F0aW9uIiwiY29ycG9yYXRpb25JZCI6OTgzODQ3MjYsImFjY291bnROYW1lIjoiQmV0aCBSaXBsZXkiLCJpc3MiOiJOZW9Db20uSW5maW5pdHkuQmFja2VuZCIsInVuaXF1ZUlkIjoidHJhbnF1aWxpdHkvOTIyMjM2NDciLCJwaWxvdElkIjo5MjIyMzY0N30.Qom8COyZB2sW3bCGm9pnGcIOqw-E2yKDsmGklQW6r9Fhu8jJpkNUv5TUhU2cJjIg5jX3082bZ6eKtRZ3z10vGw",
                    "credential": {
                        "jsonClass": "Credential",
                        "uniqueId": "tranquility/92223647",
                        "accountId": 92223647,
                        "accountName": "Adam Antinoo",
                        "assetsCount": 1476,
                        "walletBalance": 6.309543632E8,
                        "raceName": "Amarr",
                        "dataSource": "tranquility"
                    }
                };
                service.apiValidateAuthorizationToken_v1('-ANY-CODE-', '-ANY-STATE-')
                    .subscribe(response => {
                        console.log('--[apiValidateAuthorizationToken_v1]> response: ' + JSON.stringify(response));
                        expect(response).toBeDefined();
                    });


                backend.expectOne({
                    url: 'http://localhost:4200/api/v1/neocom/validateAuthorizationToken/code/-ANY-CODE-/state/-ANY-STATE-/datasource/tranquility',
                    method: 'GET'
                })[0].flush(validationResponseJson);
            })));
    });
});