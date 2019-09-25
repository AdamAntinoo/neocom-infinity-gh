// - CORE MODULES
import { NgModule } from '@angular/core';
// - BROWSER & ANIMATIONS
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
// - HTTP CLIENT
import { HttpClientModule } from '@angular/common/http';
// - NOTIFICATIONS
import { ToastrModule } from 'ng6-toastr-notifications';
// - DRAG & DROP
import { NgDragDropModule } from 'ng-drag-drop';
// - WEBSTORAGE
import { StorageServiceModule } from 'angular-webstorage-service';
// - ROUTING
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
// - SERVICES
// import { AppStoreService } from '@app/services/appstore.service';
// import { BackendService } from './services/backend.service';
// - COMPONENTS-CORE
import { AppComponent } from './app.component';

// - APPLICATION MODULES
// - PAGES
import { LoginValidationPageComponent } from './pages/login-validation-page/login-validation-page.component';
// - COMPONENTS
import { AppInfoPanelComponent } from './panels/app-info-panel/app-info-panel.component';
// - SERVICES
import { IsolationService } from './platform/isolation.service';
import { AppStoreService } from './services/appstore.service';
import { BackendService } from './services/backend.service';

// - LOCALES
import localeEs from '@angular/common/locales/es';
import { registerLocaleData } from '@angular/common';
registerLocaleData(localeEs);

// - ERROR INTERCEPTION
import * as Rollbar from 'rollbar';
import { rollbarConfig } from '@app/rollbar-errorhandler.service';
import { RollbarService } from '@app/rollbar-errorhandler.service';
import { ErrorHandler } from '@angular/core';
import { RollbarErrorHandler } from '@app/rollbar-errorhandler.service';
import { LoginValidationProgressComponent } from './panels/login-validation-progress/login-validation-progress.component';
import { LoginValidationExceptionComponent } from './panels/login-validation-exception/login-validation-exception.component';
export function rollbarFactory() {
    return new Rollbar(rollbarConfig);
}

// import { IsolationService } from '@app/platform/isolation.service';
// import { Angular6BackendService } from '@app/platform/angular6-backend.service';

@NgModule({
    imports: [
        // - BROWSER & ANIMATIONS
        FormsModule,
        ReactiveFormsModule,
        BrowserModule,
        BrowserAnimationsModule,
        // BootstrapModalModule,
        // - HTTP CLIENT
        HttpClientModule,
        // - NOTIFICATIONS
        ToastrModule.forRoot(),
        // - DRAG & DROP
        NgDragDropModule.forRoot(),
        // - WEBSTORAGE
        StorageServiceModule,
        // - APPLICATION MODULES
        // - ROUTING
        RouterModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        AppInfoPanelComponent,
        // - PAGES
        LoginValidationPageComponent,
        LoginValidationProgressComponent,
        LoginValidationExceptionComponent
    ],
    providers: [
        // - SERVICES
        { provide: IsolationService, useClass: IsolationService },
        { provide: AppStoreService, useClass: AppStoreService },
        { provide: BackendService, useClass: BackendService },
        // --- ERROR INTERCEPTION
        // { provide: ErrorHandler, useClass: RollbarErrorHandler },
        // { provide: RollbarService, useFactory: rollbarFactory },
    ],
    // exports: [
    // ],
    bootstrap: [AppComponent]
})
export class AppModule { }
