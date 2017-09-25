import { NgModule } from '@angular/core';
import { Routes } from '@angular/router';
import { RouterModule } from '@angular/router';
//--- PAGES
//import { PageSplashComponent } from './page/page-splash/page-splash.component';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { PilotRoasterPageComponent } from './pages/pilot-roaster-page/pilot-roaster-page.component';
import { PilotDetailPageComponent } from './pages/pilot-detail-page/pilot-detail-page.component';
import { PlanetaryManagerPageComponent } from './pages/planetary-manager-page/planetary-manager-page.component';
import { PlanetaryOptimizationPageComponent } from './pages/planetary-optimization-page/planetary-optimization-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  { path: 'login', component: LoginPageComponent },
  { path: 'login/:loginid/pilotroaster', component: PilotRoasterPageComponent },
  { path: 'login/:loginid/pilot/:id', component: PilotDetailPageComponent },
  { path: 'login/:loginid/pilot/:id/PlanetaryManager', component: PlanetaryManagerPageComponent },
  { path: 'login/:loginid/pilot/:id/PlanetaryManager/location/:locationid/PlanetaryOptimization', component: PlanetaryOptimizationPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
