//--- CORE MODULES
import { NgModule } from '@angular/core';
//--- BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- DRAG AND DROP
import { NgDragDropModule } from 'ng-drag-drop';
//--- OAUTH2
import { OAuthModule } from 'angular-oauth2-oidc';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BackendService } from 'src/shared/services/BackendService.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    BackendService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
