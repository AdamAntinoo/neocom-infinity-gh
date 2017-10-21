//--- CORE MODULES
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { JsonpModule } from '@angular/http';
import { CookieModule } from 'ngx-cookie';
//import { Ng2MenuModule } from 'ng2-Menu';

//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
//--- DIRECTIVES
//--- SERVICES
import { AppModelStoreService } from './services/app-model-store.service';
//--- PIPES
import { CapitalizeLetterPipe } from './pipes/capitalize-letter.pipe';
//--- PAGES
import { LoginPageComponent } from './pages/login-page/login-page.component';
//import { PilotRoasterPageComponent } from './pages/pilot-roaster-page/pilot-roaster-page.component';
import { PilotDetailPageComponent } from './pages/pilot-detail-page/pilot-detail-page.component';
import { AssetsManagerPageComponent } from './pages/assets-manager-page/assets-manager-page.component';
import { PlanetaryManagerPageComponent } from './pages/planetary-manager-page/planetary-manager-page.component';
import { PlanetaryOptimizationPageComponent } from './pages/planetary-optimization-page/planetary-optimization-page.component';
//--- COMPONENTS
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { SpinnerCentralComponent } from './components/spinner-central/spinner-central.component';
import { Pilot4RoasterComponent } from './components/pilot4-roaster/pilot4-roaster.component';
import { Pilot4DetailComponent } from './components/pilot4-detail/pilot4-detail.component';
import { PilotManagerComponent } from './components/pilot-manager/pilot-manager.component';
import { AssetsManagerComponent } from './components/assets-manager/assets-manager.component';
import { PlanetaryManagerComponent } from './components/planetary-manager/planetary-manager.component';
import { RegionComponent } from './components/region/region.component';
import { LocationComponent } from './components/location/location.component';
import { AssetComponent } from './components/asset/asset.component';
import { ResourceComponent } from './components/resource/resource.component';
import { ProcessingActionComponent } from './components/processing-action/processing-action.component';

//--- REMOVABLES
import { NavigationMenuComponent } from './component/navigation-menu/navigation-menu.component';
import { Login4ListComponent } from './components/login4-list/login4-list.component';
import { SeparatorComponent } from './components/separator/separator.component';
import { RenderComponent } from './components/render/render.component';
import { ISKNoDecimalsPipe } from './pipes/iskno-decimals.pipe';
import { NodePanelComponent } from './components/node-panel/node-panel.component';
import { Pilot4LoginComponent } from './components/pilot4-login/pilot4-login.component';
import { ContainerComponent } from './components/container/container.component';


@NgModule({
  declarations: [
    AppComponent,
    ISKNoDecimalsPipe,
    CapitalizeLetterPipe,
    HeaderComponent,
    SpinnerCentralComponent,
    NavigationMenuComponent,
    RegionComponent,
    LocationComponent,
    Pilot4RoasterComponent,
    Pilot4DetailComponent,
    PilotDetailPageComponent,
    PilotManagerComponent,
    PlanetaryManagerComponent,
    LoginPageComponent,
    Login4ListComponent,
    AssetsManagerPageComponent,
    PlanetaryManagerPageComponent,
    AssetComponent,
    SeparatorComponent,
    RenderComponent,
    AssetComponent,
    PlanetaryOptimizationPageComponent,
    ResourceComponent,
    ProcessingActionComponent,
    NodePanelComponent,
    Pilot4LoginComponent,
    ContainerComponent,
    AssetsManagerComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    CookieModule.forRoot(),
    AppRoutingModule,
  ],
  providers: [
    AppModelStoreService,
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
