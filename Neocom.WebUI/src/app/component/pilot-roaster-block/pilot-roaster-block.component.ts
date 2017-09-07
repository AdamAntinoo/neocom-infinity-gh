import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';
//--- MODELS
import { Pilot } from '../../models/Pilot.model';

@Component({
  selector: 'neocom-pilot-roaster-block',
  templateUrl: './pilot-roaster-block.component.html',
  styleUrls: ['./pilot-roaster-block.component.css']
})
export class PilotRoasterBlockComponent implements OnInit {
  @Input() pilot: Pilot;

  constructor() { }

  ngOnInit() {
  }
  // Return the ISK balance for this character but formatted to a better user presentation.
  public getAccountBalance() {
    return "ISK";
  }
}
