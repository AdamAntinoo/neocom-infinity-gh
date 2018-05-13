import { Component, OnInit } from '@angular/core';

//--- COMPONENTS
import { ExpandableComponent } from '../../components/expandable/expandable.component';

@Component({
  selector: 'neocom-ship-slot-group',
  templateUrl: './ship-slot-group.component.html',
  styleUrls: ['./ship-slot-group.component.css']
})
export class ShipSlotGroupComponent extends ExpandableComponent implements OnInit {

  constructor() {
    super();
  }

  ngOnInit() {
  }

  /**
  This kind of groups are permanently expanded
  */
  public isExpandable(): boolean {
    return false;
  }
  public clickArrow() {
  }
}
