import { Component, OnInit } from '@angular/core';
import { Input } from '@angular/core';

//--- COMPONENTS
import { PageComponent } from '../../classes/PageComponent';
//--- MODELS
import { NeoComNode } from '../../models/NeoComNode.model';

@Component({
  selector: 'neocom-expandable',
  templateUrl: './expandable.component.html',
  styleUrls: ['./expandable.component.css']
})
export class ExpandableComponent implements OnInit {
  @Input() viewer: PageComponent;
  @Input() node: NeoComNode;

  constructor() { }

  ngOnInit() {
  }

  public hasMenu(): boolean {
    return false;
  }
  public isExpandable(): boolean {
    return true;
  }
  /**
  Toggle the expand collapse status. This changes the expanded attribute and also ndicates other visual elements to change (like the arrow or the shade of the background).
  The second action is to generate again the view llist with a new call to the page component 'refreshViewPort'.
  */
  public clickArrow() {
    this.node.toggleExpanded();
    this.viewer.refreshViewPort();
  }
}
