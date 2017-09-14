import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
//--- PAGES
import { PageSplashComponent } from './page/page-splash/page-splash.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { PilotRoasterPageComponent } from './pages/pilot-roaster-page/pilot-roaster-page.component';
import { PilotDetailPageComponent } from './pages/pilot-detail-page/pilot-detail-page.component';

//--- PAGES

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: 'login', component: LoginPageComponent },
  { path: 'login/:loginid/pilotroaster', component: PilotRoasterPageComponent },
  { path: 'pilotroaster', component: PilotRoasterPageComponent },
  { path: 'pilot/:id', component: PilotDetailPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
