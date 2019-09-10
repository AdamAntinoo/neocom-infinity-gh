import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginValidationPageComponent } from './pages/login-validation-page/login-validation-page.component';
import { AppInfoPanelComponent } from './panels/app-info-panel/app-info-panel.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginValidationPageComponent,
    AppInfoPanelComponent
  ],
  exports: [
    LoginValidationPageComponent,
    AppInfoPanelComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
