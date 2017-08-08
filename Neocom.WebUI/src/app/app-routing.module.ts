import { NgModule }     from '@angular/core';
import { Routes }       from '@angular/router';
import { RouterModule } from '@angular/router';
//--- PAGES
import { PageSplashComponent }      from './page/page-splash/page-splash.component';
import { PageLoginComponent }       from './page/page-login/page-login.component';
import { PageHomeComponent }        from './page/page-home/page-home.component';
import { PagePilotsComponent }      from './page/page-pilots/page-pilots.component';
import { PagePilotDetailComponent } from './page/page-pilot-detail/page-pilot-detail.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/home',
    pathMatch: 'full'
  },
  { path: 'home', component: PageHomeComponent },
  { path: 'pilots', component: PagePilotsComponent },
  { path: 'pilot/:id', component: PagePilotDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
