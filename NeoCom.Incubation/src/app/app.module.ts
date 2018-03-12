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
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
//--- ADDITIONAL MODULES
import { ReactiveFormsModule } from '@angular/forms';
import { InlineEditorModule } from 'ng2-inline-editor';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
// import { } from 'ng2-inline-editor';
//--- ANIMATIONS
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
//--- HTTP CLIENT
import { HttpModule } from '@angular/http';
import { HttpClientModule } from '@angular/common/http';
//--- DRAG AND DROP
import { NgDragDropModule } from 'ng-drag-drop';
//--- OAUTH2
import { OAuthModule } from 'angular-oauth2-oidc';
import { OAuthService } from 'angular-oauth2-oidc';
//--- TOAST NOTIFICATIONS
// import { ToastModule } from 'ng2-toastr/ng2-toastr';
// import { ToastsManager } from 'ng2-toastr/ng2-toastr';
// import { ToastOptions } from 'ng2-toastr/ng2-toastr';
// import { ToasterModule } from 'angular5-toaster';
// import { ToasterService } from 'angular5-toaster';
// import { JsonpModule } from '@angular/http';
//--- ROUTING
import { AppRoutingModule } from './app-routing.module';
// import { OAuthService } from 'angular2-oauth2/oauth-service';
//--- APPLICATION MODULES
import { UIModule } from './modules/ui/ui.module';
// import { IncubationModule } from './modules/incubation/incubation.module';
//--- SERVICES
import { AppModelStoreService } from './services/app-model-store.service';
//--- COMPONENTS-CORE
import { AppComponent } from './app.component';
// import { ViewContainerRef } from '@angular/core';
//--- DIRECTIVES
//--- PIPES
import { CapitalizeLetterPipe } from './pipes/capitalize-letter.pipe';
import { ISKNoDecimalsPipe } from './pipes/iskno-decimals.pipe';
//--- PAGES
import { WelcomePageComponent } from './pages/welcome-page/welcome-page.component';
import { ValidateAuthorizationPageComponent } from './pages/validate-authorization-page//validate-authorization-page.component';
// import { BasePageComponent } from './pages/base-page.component';
// import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
// import { EsiAuthorizationPageComponent } from './pages/esi-authorization-page/esi-authorization-page.component';
// import { AuthorizationProgressPageComponent } from './pages/authorization-progress-page/authorization-progress-page.component';
// import { ValidateAuthorizationPageComponent } from './pages/validate-authorization-page//validate-authorization-page.component';
// import { ActionrenderingPageComponent } from './pages/actionrendering-page/actionrendering-page.component';
// import { AnimationTestingComponent } from './pages/animation-testing/animation-testing.component';
// import { DragDropPageComponent } from './pages/drag-drop-page/drag-drop-page.component';
// import { ActionsbyClassPageComponent } from './pages/actionsby-class-page/actionsby-class-page.component';
// import { FittingManagerPageComponent } from './pages/fitting-manager-page/fitting-manager-page.component';
// import { CorporationCardPageComponent } from './pages/corporation-card-page/corporation-card-page.component';
//--- COMPONENTS-ABSTRACT
// import { BasePageComponent } from './components/core/base-page/base-page.component';
//--- COMPONENTS-UI
// import { HeaderComponent } from './components/ui/header/header.component';
// import { ActionBarComponent } from './components/ui/action-bar/action-bar.component';
// import { ComponentFactoryComponent } from './components/ui/component-factory/component-factory.component';
// import { SeparatorComponent } from './components/ui/separator/separator.component';
// import { SpinnerCentralComponent } from './components/ui/spinner-central/spinner-central.component';
//--- COMPONENTS-MODEL
// import { JobsComponent } from './components/dragdrop/jobs/jobs.component';
// import { RightContainerComponent } from './components/dragdrop/right-container/right-container.component';
// import { LeftContainerComponent } from './components/dragdrop/left-container/left-container.component';
// import { ProcessingRenderPageComponent } from './pages/processing-render-page/processing-render-page.component';
// import { FittingProcessingPageComponent } from './pages/fitting-processing-page/fitting-processing-page.component';
// import { FittingDetailedComponent } from './components/detailed/fitting-detailed/fitting-detailed.component';
// import { IndustryActivitiesComponent } from './components/industry-activities/industry-activities.component';
// import { FittingRequestsComponent } from './components/fitting-requests/fitting-requests.component';
// import { InformationPanelComponent } from './components/ui/information-panel/information-panel.component';
// import { CorporationTabComponent } from './components/tabs/corporation-tab/corporation-tab.component';
// import { CorporationCardComponent } from './components/buildingblock/corporation-card/corporation-card.component';
//--- COMPONENTS-DETAILED
//--- REMOVABLES


@NgModule({
  imports: [
    //--- CORE MODULES
    BrowserModule,
    FormsModule,
    //--- ADDITIONAL MODULES
    ReactiveFormsModule,
    InlineEditorModule,
    AngularFontAwesomeModule,
    //--- ANIMATIONS
    BrowserAnimationsModule,
    //--- HTTP CLIENT
    HttpModule,
    HttpClientModule,
    //--- DRAG AND DROP
    NgDragDropModule,
    //--- OAUTH2
    OAuthModule.forRoot(),
    //--- ROUTING
    AppRoutingModule,
    //--- APPLICATION MODULES
    UIModule
  ],
  declarations: [
    //--- COMPONENTS-CORE
    AppComponent,
    //--- PIPES
    CapitalizeLetterPipe,
    ISKNoDecimalsPipe,
    //--- PAGES
    WelcomePageComponent,
    ValidateAuthorizationPageComponent
  ],
  providers: [
    // OAuthService,
    AppModelStoreService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
