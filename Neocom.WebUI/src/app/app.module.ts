import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { PageSplashComponent } from './page/page-splash/page-splash.component';
import { PageLoginComponent } from './page/page-login/page-login.component';
import { PageHomeComponent } from './page/page-home/page-home.component';

@NgModule({
  declarations: [
    AppComponent,
    PageSplashComponent,
    PageLoginComponent,
    PageHomeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
