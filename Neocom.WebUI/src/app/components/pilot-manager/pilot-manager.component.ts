import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- MODELS
import { Render } from '../../models/Render.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';
import { NeoComCharacter } from '../../models/NeoComCharacter.model';
import { Manager } from '../../models/Manager.model';

@Component({
  selector: 'neocom-pilot-manager',
  templateUrl: './pilot-manager.component.html',
  styleUrls: ['./pilot-manager.component.css']
})
export class PilotManagerComponent implements OnInit {
  @Input() pilot: NeoComCharacter;
  @Input() manager: Manager;

  constructor() { }

  ngOnInit() {
  }

}
