import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- MODELS
import { Render } from '../../models/Render.model';
import { Region } from '../../models/Region.model';
import { Location } from '../../models/Location.model';
import { EVariant } from '../../classes/EVariant.enumerated';
import { Pilot } from '../../models/Pilot.model';

@Component({
  selector: 'neocom-pilot4-detail',
  templateUrl: './pilot4-detail.component.html',
  styleUrls: ['./pilot4-detail.component.css']
})
export class Pilot4DetailComponent implements OnInit {
  @Input() pilot: Pilot;
  public expanded: boolean = true;

  constructor() { }

  ngOnInit() {
  }
  public onClickArrow(): void {
    this.expanded = !this.expanded;
  }

}
