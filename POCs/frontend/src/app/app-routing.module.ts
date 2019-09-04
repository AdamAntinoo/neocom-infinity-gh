import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginVerificationComponent } from './login-verification/login-verification.component';
import { LoginValidationComponent } from './login-validation/login-validation.component';


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
