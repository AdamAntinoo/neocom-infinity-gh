//  PROJECT:     A5POC (A5POC)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 5
//  DESCRIPTION: Proof of concept projects.
//--- CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//--- ADDITIONAL MODULES
//--- COMPONENTS-CORE
//--- DIRECTIVES
//--- SERVICES
//--- PIPES
//--- PAGES
//--- COMPONENTS-ABSTRACT
//--- COMPONENTS-UI
//--- COMPONENTS-MODEL
import { NeoComNodeComponent } from './components/neocomnode.component';
import { ExpandableComponent } from './components/expandable.component';
import { ActionComponent } from './components/action/action.component';
import { CredentialComponent } from './components/credential/credential.component';
import { JobComponent } from './components/job/job.component';
import { GroupContainerComponent } from './components/group-container/group-container.component';
//--- COMPONENTS-DETAILED
//--- MODELS
//--- REMOVABLES


@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    NeoComNodeComponent,
    ExpandableComponent,
    ActionComponent,
    CredentialComponent,
    JobComponent,
    GroupContainerComponent
  ],
  exports: [
    NeoComNodeComponent,
    ExpandableComponent,
    ActionComponent,
    CredentialComponent,
    JobComponent,
    GroupContainerComponent
  ]
})
export class NeoComModelsModule { }
