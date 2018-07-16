//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0.4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE
import { Component } from '@angular/core';
import { Input } from '@angular/core';
//--- INTERFACES
//--- SERVICES
//--- COMPONENTS
//--- MODELS

@Component({
  selector: 'neocom-action-bar',
  templateUrl: './action-bar.component.html',
  styleUrls: ['./action-bar.component.scss']
})
export class ActionBarComponent {
  @Input() titleLeft: string = "";
  @Input() titleRight: string = "";
  @Input() section: string = "-WELCOME-";

  @Input() iconTop: string = "";
  @Input() iconBottom: string = "";
  @Input() subtitle: string = "";

  //--- BAR SELECTION
  public sectionIs(section: string): boolean {
    if (this.section === section) return true;
    else return false;
  }
}
