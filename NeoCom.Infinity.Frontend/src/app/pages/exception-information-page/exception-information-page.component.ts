// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
// - SERVICES
import { AppStoreService } from '@app/services/appstore.service';
// - DOMAIN
import { BackendService } from '@app/services/backend.service';
import { environment } from '@env/environment';
import { ResponseTransformer } from '@app/services/support/ResponseTransformer';
import { ServerStatus } from '@app/domain/ServerStatus.domain';
import { NeoComException } from '@app/platform/NeoComException';

@Component({
    selector: 'exception-information-page',
    templateUrl: './exception-information-page.component.html',
    styleUrls: ['./exception-information-page.component.scss']
})
export class ExceptionInformationPageComponent implements OnInit {
    public exception: NeoComException;

    constructor(protected appStoreService: AppStoreService) { }

    ngOnInit() {
        this.exception = this.appStoreService.getLastInterceptedException();
    }
    public getExceptionType(): string {
        if (null != this.exception) return this.exception.statusText;
        else return 'UNDEFINED-EXCEPTION';
    }
    public getUserMessage(): string {
        if (null != this.exception) return this.exception.getUserMessage();
        else return '-';
    }
    public getLoginRequest(): string {
        return environment.LoginRequest;
    }
    public retryable(): boolean {
        return true;
    }
}
