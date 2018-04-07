//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';
//--- INTERFACES
import { PageComponent } from '../../../classes/PageComponent';
//--- SERVICES
import { AppModelStoreService } from '../../../services/app-model-store.service';
//--- COMPONENTS
// import { LoginPageComponent } from '../../../pages/login-page/login-page.component';
// import { ComponentFactoryComponent } from '../../factory/component-factory/component-factory.component';
//--- MODELS
// import { Login } from '../../../models/Login.model';
import { NeoComNode } from '../../../models/NeoComNode.model';
import { Pilot } from '../../../models/Pilot.model';
import { Manager } from '../../../models/Manager.model';

@Component({
  selector: 'neocom-pilot4-detail',
  templateUrl: './pilot4detail.component.html',
  styleUrls: ['./pilot4detail.component.css']
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