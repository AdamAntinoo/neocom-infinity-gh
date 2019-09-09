import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginValidationPageComponent } from './pages/login-validation-page/login-validation-page.component';


const routes: Routes = [
  {
    path: '',
    redirectTo: '/loginValidation',
    pathMatch: 'full'
  },
  // - DASHBOARD PAGES
  { path: 'loginValidation', component: LoginValidationPageComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
