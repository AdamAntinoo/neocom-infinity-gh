//--- CORE MODULES
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { JsonpModule } from '@angular/http';
import { CookieModule } from 'ngx-cookie';

//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- DIRECTIVES
//--- SERVICES
import { AppModelStoreService } from './services/app-model-store.service';
import { PilotRoasterService } from './services/pilot-roaster.service';
import { PilotListDataSourceService } from './services/pilot-list-data-source.service';
import { PilotManagersDataSourceService } from './services/pilot-managers-data-source.service';
//--- COMPONENTS
import { AppComponent } from './app.component';
import { HeaderComponent } from './component/header/header.component';
import { NavigationMenuComponent } from './component/navigation-menu/navigation-menu.component';
import { PilotRoasterBlockComponent } from './component/pilot-roaster-block/pilot-roaster-block.component';
import { RegionComponent } from './region/region.component';
import { Pilot4RoasterComponent } from './components/pilot4-roaster/pilot4-roaster.component';
import { Pilot4DetailComponent } from './components/pilot4-detail/pilot4-detail.component';
//--- PIPES
import { ISKNoDecimalsPipe } from './pipes/iskno-decimals.pipe';
import { CapitalizeLetterPipe } from './pipes/capitalize-letter.pipe';
import { PagePilotDetailComponent } from './page/page-pilot-detail/page-pilot-detail.component';
//--- PAGES
import { PageSplashComponent } from './page/page-splash/page-splash.component';
import { PageLoginComponent } from './page/page-login/page-login.component';
import { PageHomeComponent } from './page/page-home/page-home.component';
import { PagePilotsComponent } from './page/page-pilots/page-pilots.component';

import { PilotRoasterPageComponent } from './pages/pilot-roaster-page/pilot-roaster-page.component';
import { PilotDetailPageComponent } from './pages/pilot-detail-page/pilot-detail-page.component';
import { PilotManagerComponent } from './components/pilot-manager/pilot-manager.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationMenuComponent,
    PageSplashComponent,
    PageLoginComponent,
    PageHomeComponent,
    PagePilotsComponent,
    PilotRoasterBlockComponent,
    ISKNoDecimalsPipe,
    CapitalizeLetterPipe,
    PagePilotDetailComponent,
    PilotRoasterPageComponent,
    RegionComponent,
    Pilot4RoasterComponent,
    Pilot4DetailComponent,
    PilotDetailPageComponent,
    PilotManagerComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    CookieModule.forRoot(),
    AppRoutingModule
  ],
  providers: [
    AppModelStoreService,
    PilotRoasterService,
    PilotListDataSourceService,
    PilotManagersDataSourceService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
