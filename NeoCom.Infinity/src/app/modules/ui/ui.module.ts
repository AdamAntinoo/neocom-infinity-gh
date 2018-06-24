//  PROJECT:     NeoCom.Angular (NEOC.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.0.4
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
//--- CORE MODULES
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
//--- ADDITIONAL MODULES
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
// import { InlineEditorModule } from 'ng2-inline-editor';
//--- ANIMATIONS
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
//--- DRAG AND DROP
// import { NgDragDropModule } from 'ng-drag-drop';
//--- OAUTH2
//--- COMPONENTS-CORE
//--- DIRECTIVES
//--- SERVICES
//--- PIPES
import { CapitalizeLetterPipe } from './pipes/capitalize-letter.pipe';
import { ISKNoDecimalsPipe } from './pipes/iskno-decimals.pipe';
import { IskScaledPipe } from './pipes/iskscaled.pipe';
import { ArraySortPipe } from './pipes/array-sort.pipe';
//--- PAGES
//--- COMPONENTS-ABSTRACT
// import { BasePageComponent } from './base-page.component';
// import { MenuBarNodeComponent } from 'app/modules/ui/menubarnode.component';
// import { ExpandableMenuBarNodeComponent } from 'app/modules/ui/expandablemenubarnode.component';
// //--- COMPONENTS-UI
// import { ActionBarComponent } from './action-bar/action-bar.component';
import { HeaderComponent } from './header/header.component';
// import { InformationPanelComponent } from './information-panel/information-panel.component';
// import { LabeledContainerComponent } from './labeled-container/labeled-container.component';
// import { LocationNameComponent } from './location-name/location-name.component';
// import { SeparatorComponent } from './separator/separator.component';
// import { SpinnerCentralComponent } from './spinner-central/spinner-central.component';
// import { ServerStatusComponent } from './server-status/server-status.component';
// import { StyleCatalogComponent } from './style-catalog/style-catalog.component';
// import { SpinnerPanelComponent } from './spinner-panel/spinner-panel.component';

@NgModule({
  imports: [
    //--- CORE MODULES
    CommonModule,
    //--- ADDITIONAL MODULES
    FormsModule,
    ReactiveFormsModule,
    // InlineEditorModule,
    //--- ANIMATIONS
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    //--- DRAG AND DROP
    // NgDragDropModule.forRoot()
  ],
  declarations: [
    //--- PIPES
    CapitalizeLetterPipe,
    ISKNoDecimalsPipe,
    IskScaledPipe,
    ArraySortPipe,
    //--- COMPONENTS-ABSTRACT
    // BasePageComponent,
    // MenuBarNodeComponent,
    // ExpandableMenuBarNodeComponent,
    //--- COMPONENTS-UI
    HeaderComponent,
    // ActionBarComponent,
    // SeparatorComponent,
    // SpinnerCentralComponent,
    // InformationPanelComponent,
    // LabeledContainerComponent,
    // LocationNameComponent,
    // ServerStatusComponent,
    // StyleCatalogComponent,
    // SpinnerPanelComponent
  ],
  exports: [
    //--- PIPES
    CapitalizeLetterPipe,
    ISKNoDecimalsPipe,
    IskScaledPipe,
    ArraySortPipe,
    //--- COMPONENTS-ABSTRACT
    // BasePageComponent,
    // MenuBarNodeComponent,
    // ExpandableMenuBarNodeComponent,
    //--- COMPONENTS-UI
    HeaderComponent,
    // ActionBarComponent,
    // SeparatorComponent,
    // SpinnerCentralComponent,
    // InformationPanelComponent,
    // LabeledContainerComponent,
    // LocationNameComponent,
    // ServerStatusComponent,
    // StyleCatalogComponent,
    // SpinnerPanelComponent
  ]
})
export class UIModule { }
