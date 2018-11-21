//  PROJECT:     NeoCom.Infinity(NCI.A6)
//  AUTHORS:     Adam Antinoo - adamantinoo.git@gmail.com
//  COPYRIGHT:   (c) 2017-2018-2019 by Dimensinfin Industries, all rights reserved.
//  ENVIRONMENT: Angular 6.1
//  DESCRIPTION: Angular source code to run on a web server almost the same code as on the Android platform.
//               The project has 3 clear parts. One is the Java libraries that are common for all platforms,
//               the second is the java microservices that compose the web application backend made with
//               SpringBoot technology and finally the web ui code made in typescript within the Angular
//               framework.
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WelcomePageComponent } from 'src/app/modules/shared/pages/welcome-page/welcome-page.component';

// import { WelcomePageComponent } from 'projects/NeoCom-Login/src/app/pages/welcome-page/welcome-page.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: '/login',
    pathMatch: 'full'
  },
  //--- DASHBOARD PAGES
  { path: 'login', component: WelcomePageComponent },
  { path: 'welcome', component: WelcomePageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
