import { Component } from '@angular/core';
import { Input } from '@angular/core';
import { environment } from '@env/environment';

@Component({
  selector: 'app-info-panel',
  templateUrl: './app-info-panel.component.html',
  styleUrls: ['./app-info-panel.component.scss']
})
export class AppInfoPanelComponent {
  @Input() name: string;
  @Input() version: string;
  public getCopyright(): string {
    return environment.copyright;
  }
}
