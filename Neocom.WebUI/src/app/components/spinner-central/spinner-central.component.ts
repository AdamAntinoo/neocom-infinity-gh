import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';

@Component({
  selector: 'neocom-spinner-central',
  templateUrl: './spinner-central.component.html',
  styleUrls: ['./spinner-central.component.css']
})
export class SpinnerCentralComponent implements OnInit {
  @Input() configuration: any = { title: "-TITLE-", subtitle: "-SUBTITLE-" };

  constructor() { }

  ngOnInit() {
  }
  public getTitle(): string {
    if (null == this.configuration) return "-TITLE-";
    else return this.configuration.title;
  }
  public getSubTitle(): string {
    if (null == this.configuration) return "-SUBTITLE-";
    else return this.configuration.subtitle;
  }
}
