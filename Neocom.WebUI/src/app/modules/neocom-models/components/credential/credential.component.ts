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
// import { Router, ActivatedRoute, ParamMap } from '@angular/router';
// import 'rxjs/add/operator/switchMap';
import { Input } from '@angular/core';
//--- INTERFACES
// import { PageComponent } from '../PageComponent';
//--- SERVICES
// import { AppModelStoreService } from '../../../../../services/app-model-store.service';
//--- COMPONENTS
import { NeoComNodeComponent } from '../../components/neocomnode.component';
import { ExpandableComponent } from '../../components/expandable.component';
// import { ComponentFactoryComponent } from '../../factory/component-factory/component-factory.component';
//--- MODELS
import { Credential } from '../../../../models/Credential.model';
// import { NeoComNode } from '../../../../../models/NeoComNode.model';
// import { Pilot } from '../../../models/Pilot.model';

@Component({
  selector: 'neocom-credential',
  templateUrl: './credential.component.html',
  styleUrls: ['./credential.component.css']
})
export class CredentialComponent extends ExpandableComponent {
  @Input() node: Credential;
}