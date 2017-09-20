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
import { HeaderComponent } from './components/header/header.component';
import { Pilot4RoasterComponent } from './components/pilot4-roaster/pilot4-roaster.component';
import { Pilot4DetailComponent } from './components/pilot4-detail/pilot4-detail.component';
import { PilotManagerComponent } from './components/pilot-manager/pilot-manager.component';
import { PlanetaryManagerComponent } from './components/planetary-manager/planetary-manager.component';
import { RegionComponent } from './components/region/region.component';
import { LocationComponent } from './components/location/location.component';
import { AssetComponent } from './components/asset/asset.component';

//--- PIPES
import { ISKNoDecimalsPipe } from './pipes/iskno-decimals.pipe';
import { CapitalizeLetterPipe } from './pipes/capitalize-letter.pipe';
//--- PAGES
import { PageSplashComponent } from './page/page-splash/page-splash.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { PilotRoasterPageComponent } from './pages/pilot-roaster-page/pilot-roaster-page.component';
import { PilotDetailPageComponent } from './pages/pilot-detail-page/pilot-detail-page.component';
import { PlanetaryManagerPageComponent } from './pages/planetary-manager-page/planetary-manager-page.component';

//--- REMOVABLES
import { PageHomeComponent } from './page/page-home/page-home.component';
import { NavigationMenuComponent } from './component/navigation-menu/navigation-menu.component';
import { Login4ListComponent } from './components/login4-list/login4-list.component';
import { SeparatorComponent } from './components/separator/separator.component';


@NgModule({
  declarations: [
    AppComponent,
    ISKNoDecimalsPipe,
    CapitalizeLetterPipe,
    HeaderComponent,
    NavigationMenuComponent,
    PageSplashComponent,
    PageHomeComponent,
    PilotRoasterPageComponent,
    RegionComponent,
    LocationComponent,
    Pilot4RoasterComponent,
    Pilot4DetailComponent,
    PilotDetailPageComponent,
    PilotManagerComponent,
    PlanetaryManagerComponent,
    LoginPageComponent,
    Login4ListComponent,
    PlanetaryManagerPageComponent,
    AssetComponent,
    SeparatorComponent,
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
