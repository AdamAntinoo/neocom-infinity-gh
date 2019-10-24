// - CORE
import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Input } from '@angular/core';
import { environment } from '@env/environment';
// - SERVICES
import { IsolationService } from '@app/platform/isolation.service';
// - DOMAIN
import { Corporation } from '@app/domain/Corporation.domain';
import { Alliance } from '@app/domain/Alliance.domain';
import { RenderComponent } from '../render/render.component';

@Component({
    selector: 'alliance-render',
    templateUrl: './alliance-render.component.html',
    styleUrls: ['./alliance-render.component.scss']
})
export class AllianceRenderComponent extends RenderComponent {
    @Input() node: Alliance;

    public getName(): string {
        if (null != this.node) return this.node.name;
        else return '-';
    }
    public getAllianceIcon(): string {
        if (null != this.node) return this.node.getIconUrl();
        else return environment.DEFAULT_AVATAR_PLACEHOLDER;
    }
}
