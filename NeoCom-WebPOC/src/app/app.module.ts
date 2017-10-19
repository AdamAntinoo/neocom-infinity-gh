//--- CORE MODULES
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { JsonpModule } from '@angular/http';
//import { CookieModule } from 'ngx-cookie';

//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- PAGES
import { SpinnerTestPageComponent } from './pages/spinner-test-page/spinner-test-page.component';
//--- COMPONENTS
import { AppComponent } from './app.component';
import { SpinnerCentralComponent } from './components/spinner/spinner.component';
import { HeaderComponent } from './components/neocom-header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    SpinnerTestPageComponent,
    SpinnerCentralComponent,
    HeaderComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    AppRoutingModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
