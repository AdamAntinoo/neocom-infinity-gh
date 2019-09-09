import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-info-panel',
  templateUrl: './app-info-panel.component.html',
  styleUrls: ['./app-info-panel.component.scss']
})
export class AppInfoPanelComponent implements OnInit {
  @Input() name: string;
  @Input() version: string;

  constructor() { }

  ngOnInit() {
  }

}
