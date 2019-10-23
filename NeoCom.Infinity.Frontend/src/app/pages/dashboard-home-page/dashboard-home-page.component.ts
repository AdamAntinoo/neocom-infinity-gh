// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
// - DOMAIN
import { ServerStatus } from '@app/domain/ServerStatus.domain';
import { BackendService } from '@app/services/backend.service';
import { environment } from '@env/environment';

@Component({
    selector: 'dashboard-home-page',
    templateUrl: './dashboard-home-page.component.html',
    styleUrls: ['./dashboard-home-page.component.scss']
})
export class DashboardHomePageComponent implements OnInit {
    // private serverInfoSubscription: Subscription;

    constructor() { }

    ngOnInit() {
    }

}
