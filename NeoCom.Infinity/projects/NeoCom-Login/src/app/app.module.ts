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
//--- TOAST NOTIFICATIONS
// import { ToasterModule } from 'angular5-toaster';
// import { ToasterContainerComponent } from 'angular5-toaster';
// import { ToasterService } from 'angular5-toaster';
//--- ADDITIONAL MODULES
// import { ReactiveFormsModule } from '@angular/forms';
// import { InlineEditorModule } from 'ng2-inline-editor';
// import { } from 'ng2-inline-editor';
// import { AngularFontAwesomeModule } from 'angular-font-awesome';
//--- APPLICATION MODULES
// import { UIModule } from './modules/ui/ui.module';
// import { NeoComModelsModule } from './modules/neocom-models/neocom-models.module';
// import { FittingModule } from './modules/fitting/fitting.module';
// import { AssetsModule } from './modules/assets/assets.module';
// import { MenuBarModule } from './modules/menubar/menubar.module';
// import { IndustryModule } from './modules/industry/industry.module';
// import { IncubationModule } from './modules/incubation/incubation.module';
// import { PlanetaryModule } from './modules/planetary/planetary.module';

// import { IncubationModule } from './modules/incubation/incubation.module';
//--- SERVICES
// import { AppModelStoreService } from './services/app-model-store.service';

// import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WelcomePageComponent } from 'projects/NeoCom-Login/src/app/pages/welcome-page/welcome-page.component';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  declarations: [
    AppComponent,
    WelcomePageComponent
  ],
  providers: [

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
