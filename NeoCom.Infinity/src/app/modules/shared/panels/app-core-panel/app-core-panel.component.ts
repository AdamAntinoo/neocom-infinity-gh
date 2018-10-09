import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'notused-app-core-panel',
  templateUrl: './notused.html'
})
export class AppCorePanelComponent implements OnInit {
  public downloading: boolean = false; // Used to signal that that page is retrieving data.
  constructor() { }

  ngOnInit() {
  }

}
