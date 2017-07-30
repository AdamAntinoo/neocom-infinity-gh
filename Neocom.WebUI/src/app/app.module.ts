//--- CORE MODULES
import { BrowserModule }  from '@angular/platform-browser';
import { NgModule }       from '@angular/core';
import { FormsModule }    from '@angular/forms';
import { HttpModule }     from '@angular/http';
import { JsonpModule }    from '@angular/http';
import { CookieModule }   from 'ngx-cookie';

//--- ROUTING
import { AppRoutingModule }   from './app-routing.module';
//--- DIRECTIVES
//--- SERVICES
import { PilotRoasterService }  from './services/pilot-roaster.service';
//--- PAGES
import { PageSplashComponent }  from './page/page-splash/page-splash.component';
import { PageLoginComponent }   from './page/page-login/page-login.component';
import { PageHomeComponent }    from './page/page-home/page-home.component';
//--- COMPONENTS
import { AppComponent }             from './app.component';
import { HeaderComponent }          from './component/header/header.component';
import { NavigationMenuComponent }  from './component/navigation-menu/navigation-menu.component';
import { PagePilotsComponent }      from './page/page-pilots/page-pilots.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavigationMenuComponent,
    PageSplashComponent,
    PageLoginComponent,
    PageHomeComponent,
    PagePilotsComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    JsonpModule,
    CookieModule.forRoot(),
    AppRoutingModule
  ],
  providers: [PilotRoasterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
