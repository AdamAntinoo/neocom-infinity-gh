import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginVerificationComponent } from './login-verification/login-verification.component';
import { LoginValidationComponent } from './login-validation/login-validation.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginVerificationComponent,
    LoginValidationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
