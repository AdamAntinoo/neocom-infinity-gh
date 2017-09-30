import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Rx';
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
  public timer;

  constructor() {
  }

  ngOnInit() {
  }
  public getTitle(): string {
    if (null == this.configuration) return "-TITLE-";
    else return this.configuration.title;
  }
  public getSubTitle(): string {
    if (null == this.configuration) return this.timer;
    else return this.configuration.subtitle + " - " + this.timer;
  }
  public timeLapseTimer() {
    Observable.interval(1000)
      .map((x) => x + 1)
      .subscribe((x) => {
        this.timer = x;
      });
  }
  public checkTimer() {
    if (this.configuration.subtitle == "-TIMER-") {
      this.timeLapseTimer();
      return true;
    } else return false;
  }
}
