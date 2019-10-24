// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
// - DOMAIN
import { RenderComponent } from '../render/render.component';
import { Pilot } from '@app/domain/Pilot.domain';

@Component({
    selector: 'pilot-render',
    templateUrl: './pilot-render.component.html',
    styleUrls: ['./pilot-render.component.scss']
})
export class PilotRenderComponent extends RenderComponent {
    @Input() node: Pilot;
}
