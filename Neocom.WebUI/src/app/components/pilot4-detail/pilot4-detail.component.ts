import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- CLASSES
import { PageComponent } from '../../classes/PageComponent';
//--- MODELS
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';

@Component({
  selector: 'neocom-pilot4-detail',
  templateUrl: './pilot4-detail.component.html',
  styleUrls: ['./pilot4-detail.component.css']
})
export class Pilot4DetailComponent extends PageComponent implements OnInit {
  @Input() pilot: Pilot;

  constructor() {
    super();
  }

  ngOnInit() {
  }
  public getCharacterClass(): string {
    if (this.pilot.corporation == true) return "CORPORATION";
    else return "PILOT";
  }
}
