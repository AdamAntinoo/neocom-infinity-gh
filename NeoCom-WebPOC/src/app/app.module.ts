//--- CORE MODULES
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { JsonpModule } from '@angular/http';

//--- SERVICES
import { AppModelStoreService } from './services/app-model-store.service';
//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- PAGES
import { PageSelectorComponent } from './pages/page-selector/page-selector.component';
import { SpinnerTestPageComponent } from './pages/spinner-test-page/spinner-test-page.component';
import { CustomSerializationPageComponent } from './pages/custom-serialization-page/custom-serialization-page.component';
//--- COMPONENTS
import { AppComponent } from './app.component';
import { SpinnerCentralComponent } from './components/spinner/spinner.component';
import { HeaderComponent } from './components/neocom-header/header.component';

@NgModule({
  declarations: [
    AppComponent,
    PageSelectorComponent,
    SpinnerTestPageComponent,
    CustomSerializationPageComponent,
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
  providers: [
    AppModelStoreService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
