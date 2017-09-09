import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
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
  public expanded: boolean = false;

  constructor(private route: ActivatedRoute, private router: Router) { }

  ngOnInit() {
  }
  // public onClick(): void {
  //   this.route.paramMap
  //     .switchMap((params: ParamMap) =>
  //       this.pilotRoasterService.getPilotDetails(params.get('id')))
  //     .subscribe((pilot: Pilot) => this.pilot = pilot);
  // }
  public getCharacterClass(): string {
    if (this.model.corporation == true) return "CORPORATION";
    else return "PILOT";
  }
  public onClickArrow(): void {
    this.expanded = !this.expanded;
  }
}
