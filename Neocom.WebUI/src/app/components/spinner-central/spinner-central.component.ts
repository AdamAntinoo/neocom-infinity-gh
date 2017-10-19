import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';
import { MomentModule } from 'angular2-moment';

@Component({
  selector: 'neocom-spinner-central',
  templateUrl: './spinner-central.component.html',
  styleUrls: ['./spinner-central.component.css']
})
export class SpinnerCentralComponent implements OnInit {
  @Input() configuration: any = { title: "-TITLE-", subtitle: "-SUBTITLE-" };
  public timer: any = null;
  private ticks = 0;

  constructor() {
  }

  ngOnInit() {
    this.timer = Observable.timer(2000, 1000);
    this.timer.subscribe(t => {
      this.ticks = t;
    });
  }
  public getTitle(): string {
    if (null == this.configuration) return "-TITLE-";
    else return this.configuration.title;
  }
  public getWaitingTime() {
    var date = new Date(null);
    date.setSeconds(this.ticks);
    return date;
  }
}
