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
//--- PIPES
import { CapitalizeLetterPipe } from './pipes/capitalize-letter.pipe';
import { ISKNoDecimalsPipe } from './pipes/iskno-decimals.pipe';
//--- PAGES
import { CredentialsPageComponent } from './pages/credentials-page/credentials-page.component';
import { PilotDetailPageComponent } from './pages/pilot-detail-page/pilot-detail-page.component';
import { FittingManagerPageComponent } from './pages/fitting-manager-page/fitting-manager-page.component';
//--- COMPONENTS-ABSTRACT
import { BasePageComponent } from './components/core/base-page/base-page.component';
//--- COMPONENTS-CORE
import { AppComponent } from './app.component';
//--- COMPONENTS-UI
import { HeaderComponent } from './components/ui/header/header.component';
import { ActionBarComponent } from './components/ui/action-bar/action-bar.component';
import { ComponentFactoryComponent } from './components/factory/component-factory/component-factory.component';
import { DetailedContainerComponent } from './components/factory/detailed-container/detailed-container.component';
import { SpinnerCentralComponent } from './components/spinner-central/spinner-central.component';
//--- COMPONENTS-MODEL
import { SeparatorComponent } from './components/ui/separator/separator.component';
import { CredentialComponent } from './components/model/credential/credential.component';
import { ExpandableNodeComponent } from './components/model/expandable-node/expandable-node.component';
import { FittingComponent } from './components/model/fitting/fitting.component';
import { GroupContainerComponent } from './components/model/group-container/group-container.component';
import { Manager4PilotComponent } from './components/model/manager4pilot/manager4pilot.component';
//--- COMPONENTS-DETAILED

// import { Pilot4RoasterComponent } from './components/pilot4-roaster/pilot4-roaster.component';
import { Pilot4DetailComponent } from './components/model/pilot4detail/pilot4detail.component';
import { PilotManagerComponent } from './components/pilot-manager/pilot-manager.component';
import { AssetsManagerComponent } from './components/assets-manager/assets-manager.component';
import { PlanetaryManagerComponent } from './components/planetary-manager/planetary-manager.component';
import { RegionComponent } from './components/region/region.component';
import { LocationComponent } from './components/location/location.component';
import { AssetComponent } from './components/asset/asset.component';
import { ResourceComponent } from './components/resource/resource.component';
import { ProcessingActionComponent } from './components/processing-action/processing-action.component';

//--- REMOVABLES
// import { NavigationMenuComponent } from './component/navigation-menu/navigation-menu.component';
// import { Login4ListComponent } from './components/login4-list/login4-list.component';
// import { NodePanelComponent } from './components/node-panel/node-panel.component';
// import { Pilot4LoginComponent } from './components/pilot4-login/pilot4-login.component';
import { ContainerComponent } from './components/container/container.component';
import { ShipComponent } from './components/ship/ship.component';
import { ShipSlotGroupComponent } from './components/ship-slot-group/ship-slot-group.component';
// import { FittingManagerPageComponent } from './pages/fitting-manager-page/fitting-manager-page.component';
// import { LoginPageComponent } from './pages/login-page/login-page.component';
// //import { PilotRoasterPageComponent } from './pages/pilot-roaster-page/pilot-roaster-page.component';
// import { AssetsManagerPageComponent } from './pages/assets-manager-page/assets-manager-page.component';
// import { PlanetaryManagerPageComponent } from './pages/planetary-manager-page/planetary-manager-page.component';
// import { PlanetaryOptimizationPageComponent } from './pages/planetary-optimization-page/planetary-optimization-page.component';
import { ExpandableComponent } from './components/expandable/expandable.component';
import { FittingItemComponent } from './components/model/fitting-item/fitting-item.component';


@NgModule({
  declarations: [
    CapitalizeLetterPipe,
    ISKNoDecimalsPipe,
    CredentialsPageComponent,
    AppComponent,
    // BasePageComponent,
    HeaderComponent,
    ActionBarComponent,
    ComponentFactoryComponent,
    DetailedContainerComponent,
    SpinnerCentralComponent,
    SeparatorComponent,
    CredentialComponent,
    ExpandableNodeComponent,
    FittingComponent,
    GroupContainerComponent,
    Manager4PilotComponent,

    // NavigationMenuComponent,
    RegionComponent,
    LocationComponent,
    // Pilot4RoasterComponent,
    Pilot4DetailComponent,
    PilotDetailPageComponent,
    PilotManagerComponent,
    PlanetaryManagerComponent,
    // LoginPageComponent,
    // Login4ListComponent,
    // AssetsManagerPageComponent,
    // PlanetaryManagerPageComponent,
    AssetComponent,
    AssetComponent,
    // PlanetaryOptimizationPageComponent,
    ResourceComponent,
    ProcessingActionComponent,
    // NodePanelComponent,
    // Pilot4LoginComponent,
    ContainerComponent,
    AssetsManagerComponent,
    ShipComponent,
    ShipSlotGroupComponent,
    ExpandableComponent,
    FittingManagerPageComponent,
    FittingItemComponent,
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
