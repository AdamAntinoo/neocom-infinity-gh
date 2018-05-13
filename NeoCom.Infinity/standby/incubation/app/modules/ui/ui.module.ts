//  PROJECT:     NeoCom.WS (NEOC.WS)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 4
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
import { InlineEditorModule } from 'ng2-inline-editor';
//--- ANIMATIONS
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
// import { HttpModule } from '@angular/http';
// import { HttpClientModule } from '@angular/common/http';
//--- DRAG AND DROP
import { NgDragDropModule } from 'ng-drag-drop';
//--- OAUTH2
// import { OAuthModule } from 'angular-oauth2-oidc';
//--- COMPONENTS-CORE
//--- DIRECTIVES
//--- SERVICES
//--- PIPES
import { CapitalizeLetterPipe } from '../../pipes/capitalize-letter.pipe';
import { ISKNoDecimalsPipe } from '../../pipes/iskno-decimals.pipe';
//--- PAGES
//--- COMPONENTS-ABSTRACT
//--- COMPONENTS-UI
import { HeaderComponent } from './header/header.component';
import { ActionBarComponent } from './action-bar/action-bar.component';
import { SeparatorComponent } from './separator/separator.component';
//--- COMPONENTS-MODEL
//--- COMPONENTS-DETAILED
//--- MODELS
//--- REMOVABLES


@NgModule({
  imports: [
    //--- CORE MODULES
    CommonModule,
    //--- ADDITIONAL MODULES
    FormsModule,
    ReactiveFormsModule,
    InlineEditorModule,
    //--- ANIMATIONS
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    //--- DRAG AND DROP
    NgDragDropModule.forRoot()
    //--- OAUTH2
  ],
  declarations: [
    HeaderComponent,
    ActionBarComponent,
    SeparatorComponent
  ],
  exports: [
    HeaderComponent,
    ActionBarComponent,
    SeparatorComponent
  ]
})
export class UIModule { }
