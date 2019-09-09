import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
  {
    path: '',
    redirectTo: '/loginValidation',
    pathMatch: 'full'
  },
  //--- DASHBOARD PAGES
  { path: 'loginValidation', component: LoginValidationComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
