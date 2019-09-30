// - CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { environment } from '@env/environment';
import { Inject } from '@angular/core';
import { ViewChild } from '@angular/core';

@Component({
  selector: 'app-info-panel',
  templateUrl: './app-info-panel.component.html',
  styleUrls: ['./app-info-panel.component.scss']
})
export class AppInfoPanelComponent {
  @Input() name: string;
  @Input() version: string;

  public getName(): string {
    return this.name;
  }
  public getVersion(): string {
    return this.version;
  }
  public getCopyright(): string {
    return environment.copyright;
  }
}
