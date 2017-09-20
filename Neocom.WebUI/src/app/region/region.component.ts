import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- MODELS
import { Render } from '../models/Render.model';
import { Region } from '../models/Region.model';
import { Location } from '../models/Location.model';
import { EVariant } from '../classes/EVariant.enumerated';

@Component({
  selector: 'app-region',
  templateUrl: './region.component.html',
  styleUrls: ['./region.component.css']
})
export class RegionComponent implements OnInit {
  @Input() node: Render;
  public locations: Location[] = [];

  constructor() { }

  ngOnInit() {
    // let l = new Location({ name: "Location Inside 1", position: "Position 1" });
    // this.locations.push(l);
  }
  public toggleExpanded() {
    this.node.toggleExpanded();
  }
}
