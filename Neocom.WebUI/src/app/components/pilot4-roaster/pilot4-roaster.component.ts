import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';
//--- MODELS
import { Render } from '../../models/Render.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';

@Component({
  selector: 'neocom-pilot4-roaster',
  templateUrl: './pilot4-roaster.component.html',
  styleUrls: ['./pilot4-roaster.component.css'],
  inputs: ['model']
})
export class Pilot4RoasterComponent implements OnInit {
  @Input() model: Pilot;

  constructor() { }

  ngOnInit() {
  }
  public onClick(): void {
    // Mode to another page to see this charaacter selected.
  }
  public getCharacterClass(): string {
    if (this.model.corporation == true) return "CORPORATION";
    else return "PILOT";
  }
}
